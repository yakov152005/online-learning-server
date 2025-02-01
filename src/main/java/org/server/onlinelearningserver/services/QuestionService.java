package org.server.onlinelearningserver.services;

import org.server.onlinelearningserver.dtos.CategoryProgressDto;
import org.server.onlinelearningserver.dtos.CategorySuccessStreakDto;
import org.server.onlinelearningserver.dtos.QuestionDto;
import org.server.onlinelearningserver.dtos.WeakPointDto;
import org.server.onlinelearningserver.entitys.Progress;
import org.server.onlinelearningserver.entitys.Question;
import org.server.onlinelearningserver.entitys.QuestionHistory;
import org.server.onlinelearningserver.entitys.User;
import org.server.onlinelearningserver.repositoris.ProgressRepository;
import org.server.onlinelearningserver.repositoris.QuestionHistoryRepository;
import org.server.onlinelearningserver.repositoris.QuestionRepository;
import org.server.onlinelearningserver.repositoris.UserRepository;
import org.server.onlinelearningserver.responses.DashboardResponse;
import org.server.onlinelearningserver.responses.QuestionResponse;
import org.server.onlinelearningserver.responses.SubmitResponse;
import org.server.onlinelearningserver.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.server.onlinelearningserver.utils.Constants.Question.*;

@Service
public class QuestionService {

    private final QuestionHistoryRepository questionHistoryRepository;
    private final ProgressRepository progressRepository;
    private final QuestionGenerator questionGenerator;
    private final UserRepository userRepository;
    private final QuestionRepository questionRepository;

    @Autowired
    public QuestionService(QuestionHistoryRepository questionHistoryRepository, ProgressRepository progressRepository
                           , QuestionGenerator questionGenerator, UserRepository userRepository,
                           QuestionRepository questionRepository
    ){
        this.questionHistoryRepository = questionHistoryRepository;
        this.progressRepository = progressRepository;
        this.questionGenerator = questionGenerator;
        this.userRepository = userRepository;
        this.questionRepository = questionRepository;
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


        QuestionDto questionDto = new QuestionDto(
                question.getId(),
                question.getCategory(),
                question.getContent(),
                question.getDifficulty(),
                question.getSolution(),
                question.getExplanation()
        );

        return new QuestionResponse(true, "Question sent successfully.", questionDto,successStreak);
    }



    public SubmitResponse submitAnswer(long questionId, String userAnswer, String username){
        User user = userRepository.findByUsername(username);
        if (user == null){
            return new SubmitResponse(false,"User not found.",false);
        }

        Question question = questionRepository.findById(questionId).orElse(null);
        if (question == null){
            return new SubmitResponse(false,"Question not found.",false);
        }


        question.setAnswered(true);
        boolean isCorrect = question.getSolution().equalsIgnoreCase(userAnswer);

        saveQuestionHistory(user,question,isCorrect);
        Map<String, Object> progressStatus = updateProgress(user, isCorrect, question.getCategory());

        boolean isLevelUp = (boolean) progressStatus.get("isLevelUp");
        boolean isLevelDown = (boolean) progressStatus.get("isLevelDown");
        Map<String, Integer> successStreaksByCategory = (Map<String, Integer>) progressStatus.get("successStreaksByCategory");
        int coinsCredits = user.getCoinsCredits();

        return new SubmitResponse(
                isCorrect,
                isCorrect ? "Correct answer!" : "Wrong answer.",
                isLevelUp,
                isLevelDown,
                isCorrect ? "" : question.getSolution(),
                successStreaksByCategory,
                coinsCredits
        );
    }



    @Transactional
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
        double totalSuccessRate =  ( (totalCorrect / (double) totalAnswers) * 100);

        return new DashboardResponse(true,
                "All details send."
                ,successStreaks,
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
    }


}
