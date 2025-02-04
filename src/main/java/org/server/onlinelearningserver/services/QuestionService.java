package org.server.onlinelearningserver.services;

import org.server.onlinelearningserver.controllers.StreamController;
import org.server.onlinelearningserver.dtos.*;
import org.server.onlinelearningserver.entitys.*;
import org.server.onlinelearningserver.repositoris.*;
import org.server.onlinelearningserver.responses.DashboardResponse;
import org.server.onlinelearningserver.responses.QuestionResponse;
import org.server.onlinelearningserver.responses.SubmitResponse;
import org.server.onlinelearningserver.services.generator.QuestionGenerator;
import org.server.onlinelearningserver.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



import java.util.*;

import static org.server.onlinelearningserver.utils.Constants.Question.*;

@Service
public class QuestionService {

    private final QuestionHistoryRepository questionHistoryRepository;
    private final ProgressRepository progressRepository;
    private final QuestionGenerator questionGenerator;
    private final UserRepository userRepository;
    private final QuestionRepository questionRepository;
    private final WeeklyStatsRepository weeklyStatsRepository;
    private final StreamController streamController;

    @Autowired
    public QuestionService(QuestionHistoryRepository questionHistoryRepository, ProgressRepository progressRepository
                           , QuestionGenerator questionGenerator, UserRepository userRepository,
                           QuestionRepository questionRepository,WeeklyStatsRepository weeklyStatsRepository,
                           StreamController streamController
    ){
        this.questionHistoryRepository = questionHistoryRepository;
        this.progressRepository = progressRepository;
        this.questionGenerator = questionGenerator;
        this.userRepository = userRepository;
        this.questionRepository = questionRepository;
        this.weeklyStatsRepository = weeklyStatsRepository;
        this.streamController = streamController;
    }


    public QuestionResponse generateQuestion(String username, String category) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            return new QuestionResponse(false, "User not found.", null);
        }

        Progress progress = progressRepository.findByUser(user);
        if (progress == null) {
            progress = new Progress();
            progress.setUser(user);
            progress.getCategoryProgress().put(category, 1);
            progress.setActiveCategory(category);
            progress.getCategorySuccessStreak().put(category,0);
            progressRepository.save(progress);
        }


        if (progress.getActiveCategory() == null || !progress.getActiveCategory().equals(category)) {
            progress.setActiveCategory(category);
            progress.getCategoryProgress().putIfAbsent(category, 1);
            progress.getCategorySuccessStreak().putIfAbsent(category, 0);

            progressRepository.save(progress);
        }

        String activeCategory = progress.getActiveCategory();
        int difficulty = progress.getCategoryProgress().getOrDefault(activeCategory, 1);


        Question question = questionGenerator.generateQuestion(activeCategory, difficulty);
        question.setProgress(progress);
        questionRepository.save(question);

        int successStreak = progress.getCategorySuccessStreak().getOrDefault(activeCategory, 0);
        int currentLevel = progress.getCategoryProgress().getOrDefault(activeCategory, 1);


        QuestionDto questionDto = new QuestionDto(
                question.getId(),
                question.getCategory(),
                question.getContent(),
                question.getDifficulty(),
                question.getSolution(),
                question.getExplanation(),
                question.getSteps()
        );

        return new QuestionResponse(true, "Question sent successfully.", questionDto,successStreak,currentLevel);
    }



    public SubmitResponse submitAnswer(long questionId, String userAnswer, String username){
        User user = userRepository.findByUsername(username);
        if (user == null){
            return new SubmitResponse(false,"User not found.");
        }

        Question question = questionRepository.findById(questionId).orElse(null);
        if (question == null){
            return new SubmitResponse(false,"Question not found.");
        }


        question.setAnswered(true);
        boolean isCorrect = question.getSolution().equalsIgnoreCase(userAnswer);

        saveQuestionHistory(user,question,isCorrect);
        Map<String, Object> progressStatus = updateProgress(user, isCorrect, question.getCategory());

        boolean isLevelUp = (boolean) progressStatus.get("isLevelUp");
        boolean isLevelDown = (boolean) progressStatus.get("isLevelDown");
        Map<String, Integer> successStreaksByCategory = (Map<String, Integer>) progressStatus.get("successStreaksByCategory");
        int coinsCredits = user.getCoinsCredits();
        Map<String, Integer> currentLevelByCategory = (Map<String, Integer>) progressStatus.get("currentLevelByCategory");

        return new SubmitResponse(
                isCorrect,
                isCorrect ? "Correct answer!" : "Wrong answer.",
                isLevelUp,
                isLevelDown,
                isCorrect ? "" : question.getSolution(),
                successStreaksByCategory,
                coinsCredits,
                currentLevelByCategory
        );
    }


    public Map<String, Object> updateProgress(User user, boolean isCorrect, String activeCategory) {
        Map<String, Object> progressStatus = new HashMap<>();
        progressStatus.put("isLevelUp", false);
        progressStatus.put("isLevelDown", false);

        Progress progress = progressRepository.findByUser(user);
        if (progress == null) {
            throw new IllegalStateException("Progress not found for user: " + user.getUsername());
        }

        int currentLevel = progress.getCategoryProgress().getOrDefault(activeCategory, 1);
        int successStreak = progress.getCategorySuccessStreak().getOrDefault(activeCategory, 0);
        int coinsBefore = user.getCoinsCredits(); // כדי לבדוק אם היה שינוי


        // בדיקה האם המשתמש היה ברמה הזו בעבר
        int REQUIRED_STREAK = FOR_LEVEL_UP; // לא היה ברמה הבאה
        if (questionHistoryRepository.wasLevelReached(user, activeCategory, currentLevel + 1)){
            REQUIRED_STREAK = USER_WAS_AT_THE_NEXT_LEVEL; // היה ברמה הבאה אז יותר קל לעלות אלייה כדי שלא ישתעמם
        }

        boolean levelUp = false;
        boolean levelDown = false;

        if (isCorrect) {
            successStreak++;
            if (successStreak >= REQUIRED_STREAK) {
                progress.getCategoryProgress().put(activeCategory, currentLevel + 1);
                levelUp = true;

                user.setCoinsCredits(user.getCoinsCredits() + 1);

                successStreak = RESET_AFTER_LEVEL_UP;
            }
        } else {
            successStreak = RESET_AFTER_ERROR_ANSWER;


            Map<String, Integer> weakPoints = progress.getWeakPoints();
            weakPoints.put(activeCategory, weakPoints.getOrDefault(activeCategory, 0) + 1);
            progress.setWeakPoints(weakPoints);


            int weaknessPoint = progress.getWeakPoints().get(activeCategory);

            if (weaknessPoint == 5) {
                streamController.notifyUser(user.getUsername(),
                        " ⚠️ אתה מתקשה בנושא: " + activeCategory + ", מומלץ לתרגל יותר! ⚠️");
            }

            if (weaknessPoint > ERROR_HIGH_THEN_FIVE) {
                double weaknessFactor = progress.getWeakPoints().getOrDefault(activeCategory, 0) / 10.0;
                if (Math.random() < weaknessFactor) {
                    if (currentLevel > LEVEL_HIGH_THEN_ONE) {
                        progress.getCategoryProgress().put(activeCategory, currentLevel - 1);
                        levelDown = true;
                        weakPoints.put(activeCategory, 0);
                        progress.setWeakPoints(weakPoints);
                    }
                }
            }
        }

        progress.getCategorySuccessStreak().put(activeCategory, successStreak);

        if (levelUp || levelDown || user.getCoinsCredits() != coinsBefore) {
            userRepository.save(user);
        }
        progressRepository.save(progress);

        progressStatus.put("isLevelUp", levelUp);
        progressStatus.put("isLevelDown", levelDown);
        progressStatus.put("successStreaksByCategory", progress.getCategorySuccessStreak());
        progressStatus.put("currentLevelByCategory", progress.getCategoryProgress());

        return progressStatus;
    }

    public void saveQuestionHistory(User user, Question question, boolean isCorrect) {
        QuestionHistory history = new QuestionHistory();
        history.setUser(user);
        history.setQuestion(question);
        history.setCorrect(isCorrect);
        history.setAnsweredAt(new Date());
        questionHistoryRepository.save(history);
    }

    public List<QuestionDto> getUnansweredQuestions(User user) {
        List<Question> unansweredQuestions = questionRepository.findByProgress_UserAndAnsweredFalse(user);
        return unansweredQuestions.stream()
                .map(q -> new QuestionDto(q.getId(),q.getCategory(), q.getContent(), q.getDifficulty()))
                .toList();
    }

    public List<QuestionDto> getQuestionsAnsweredIncorrectly(User user){
        List<QuestionHistory> answeredIncorrectHistories  = questionHistoryRepository.findByUser(user)
                .stream()
                .filter(history-> !history.isCorrect())
                .toList();

        return answeredIncorrectHistories.stream()
                .map(h -> new QuestionDto(
                        h.getQuestion().getId(),
                        h.getQuestion().getCategory(),
                        h.getQuestion().getContent(),
                        h.getQuestion().getDifficulty()))
                .toList();
    }

    public DashboardResponse getDashboard(String token,String username) {
        User user = userRepository.findByUsername(username);

        if (token == null){
            return new DashboardResponse(false,"token is missing");
        }
        if (user == null){
            return new DashboardResponse(false,"User not found.");
        }

        String cleanToken = token.replace("Bearer ", "");
        boolean isValid = JwtUtils.isTokenValid(cleanToken);
        String findUsernameByToken = "";

        if (isValid){
            findUsernameByToken = JwtUtils.extractUsername(cleanToken);
        }else {
            return new DashboardResponse(false,"User name not founded");
        }

        User findUserByToken = userRepository.findByUsername(findUsernameByToken);

        if (user != findUserByToken){
            return new DashboardResponse(false,"Token not match to this account please login again");
        }

        return buildDashboardResponse(user);
    }

    public DashboardResponse getDashboardAdmin(String token, String username, String targetUsername) {
        User requestingUser = userRepository.findByUsername(username);

        if (token == null) {
            return new DashboardResponse(false, "Token is missing");
        }
        if (requestingUser == null) {
            return new DashboardResponse(false, "User not found.");
        }

        String cleanToken = token.replace("Bearer ", "");
        boolean isValid = JwtUtils.isTokenValid(cleanToken);
        String findUsernameByToken = "";

        if (isValid) {
            findUsernameByToken = JwtUtils.extractUsername(cleanToken);
        } else {
            return new DashboardResponse(false, "Invalid token, please login again");
        }

        User findUserByToken = userRepository.findByUsername(findUsernameByToken);

        if (!findUserByToken.equals(requestingUser)) {
            return new DashboardResponse(false, "Token does not match user, please login again");
        }

        if (requestingUser.getUsername().equalsIgnoreCase("admin") && targetUsername != null) {

            User targetUser = userRepository.findByUsername(targetUsername);
            if (targetUser == null) {
                return new DashboardResponse(false, "Target user not found.");
            }
            return buildDashboardResponse(targetUser);
        }
        return buildDashboardResponse(requestingUser);
    }

    private DashboardResponse buildDashboardResponse(User user){
        List<QuestionDto> openQuestions = getUnansweredQuestions(user);
        List<QuestionDto> questionAnsweredIncorrectly = getQuestionsAnsweredIncorrectly(user);
        List<WeakPointDto> weakPoints = progressRepository.findWeakPointsByUser(user);
        List<CategorySuccessStreakDto> successStreaks = progressRepository.findSuccessStreakByUser(user);
        List<CategoryProgressDto> currentLevels = progressRepository.findCategoryProgressByUser(user);


        Map<String, Integer> correctAnswersPerCategory = new HashMap<>();
        Map<String, Integer> incorrectAnswersPerCategory = new HashMap<>();
        List<QuestionHistory> history = questionHistoryRepository.findByUser(user);

        int totalCorrect = 0;
        int totalIncorrect = 0;

        for (QuestionHistory qh : history) {
            String category = qh.getQuestion().getCategory();
            if (qh.isCorrect()) {
                correctAnswersPerCategory.put(category, correctAnswersPerCategory.getOrDefault(category, 0) + 1);
                totalCorrect++;
            } else {
                incorrectAnswersPerCategory.put(category, incorrectAnswersPerCategory.getOrDefault(category, 0) + 1);
                totalIncorrect++;
            }
        }


        Map<String, Double> successRates = new HashMap<>();
        for (String category : correctAnswersPerCategory.keySet()) {
            int correct = correctAnswersPerCategory.getOrDefault(category, 0);
            int incorrect = incorrectAnswersPerCategory.getOrDefault(category, 0);
            int total = correct + incorrect;

            successRates.put(category, total == 0 ? 0 : (correct / (double) total) * 100);
        }

        int totalUnanswered = openQuestions.size();
        int totalAnswers = totalCorrect + totalIncorrect;

        double totalSuccessRate = 0;
        if (totalAnswers > 0) {
            totalSuccessRate = ( (totalCorrect / (double) totalAnswers) * 100);
        }


        saveWeeklyStats(user, totalSuccessRate);


        DashboardDto dashboardDto = new DashboardDto(
                successStreaks,
                openQuestions,
                questionAnsweredIncorrectly,
                weakPoints,
                currentLevels,
                correctAnswersPerCategory,
                incorrectAnswersPerCategory,
                successRates,
                totalCorrect,
                totalIncorrect,
                totalUnanswered,
                totalSuccessRate
        );

        return new DashboardResponse(true,
                "All details send.",
                dashboardDto
        );
    }


    private void saveWeeklyStats(User user, double totalSuccessRate) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, -7);
        Date weekStart = calendar.getTime();


        WeeklyStats latestStats = weeklyStatsRepository.findLatestByUser(user).stream().findFirst().orElse(null);


        if (latestStats == null) {
            WeeklyStats firstStats = new WeeklyStats();
            firstStats.setUser(user);
            firstStats.setCurrentTotalSuccessRate(totalSuccessRate);
            firstStats.setPreviousTotalSuccessRate(0.0);
            firstStats.setRecordedAt(new Date());
            firstStats.setPreviousWeekDate(null);
            weeklyStatsRepository.save(firstStats);
            System.out.println("✅ נתון ראשוני נשמר למשתמש: " + user.getUsername());
            return;
        }


        if (latestStats.getRecordedAt().after(weekStart)) {
            System.out.println("⏳ נתונים כבר נשמרו השבוע, אין צורך לשמור שוב.");
            return;
        }


        double previousSuccessRate = latestStats.getCurrentTotalSuccessRate();

        latestStats.setPreviousTotalSuccessRate(previousSuccessRate);
        latestStats.setCurrentTotalSuccessRate(totalSuccessRate);
        latestStats.setPreviousWeekDate(latestStats.getRecordedAt());
        latestStats.setRecordedAt(new Date());

        weeklyStatsRepository.save(latestStats);

        System.out.println("✅ נתון שבועי עודכן למשתמש: " + user.getUsername());
    }


}
