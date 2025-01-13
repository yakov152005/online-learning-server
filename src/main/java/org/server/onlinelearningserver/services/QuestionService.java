package org.server.onlinelearningserver.services;

import org.server.onlinelearningserver.dtos.QuestionDto;
import org.server.onlinelearningserver.entitys.Progress;
import org.server.onlinelearningserver.entitys.Question;
import org.server.onlinelearningserver.entitys.QuestionHistory;
import org.server.onlinelearningserver.entitys.User;
import org.server.onlinelearningserver.repositoris.ProgressRepository;
import org.server.onlinelearningserver.repositoris.QuestionHistoryRepository;
import org.server.onlinelearningserver.repositoris.QuestionRepository;
import org.server.onlinelearningserver.repositoris.UserRepository;
import org.server.onlinelearningserver.responses.BasicResponse;
import org.server.onlinelearningserver.responses.QuestionResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.Date;
import java.util.Map;

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

        double weaknessFactor = progress.getWeakPoints().getOrDefault(activeCategory, 0) / 10.0;
        if (Math.random() < weaknessFactor) {
            difficulty = Math.max(difficulty - 1, 1);
        }

        Question question = questionGenerator.generateQuestion(activeCategory, difficulty);
        questionRepository.save(question);

        QuestionDto questionDto = new QuestionDto(
                question.getId(),
                question.getCategory(),
                question.getContent(),
                question.getDifficulty(),
                question.getSolution(),
                question.getExplanation()
        );

        return new QuestionResponse(true, "Question sent successfully.", questionDto);
    }



    public BasicResponse submitAnswer(long questionId, String userAnswer, String username){
        User user = userRepository.findByUsername(username);
        if (user == null){
            return new BasicResponse(false,"User not found.");
        }

        Question question = questionRepository.findById(questionId).orElse(null);
        if (question == null){
            return new BasicResponse(false,"Question not found.");
        }

        boolean isCorrect = question.getSolution().equals(userAnswer);

        saveQuestionHistory(user,question,isCorrect);
        updateProgress(user,isCorrect,question.getCategory());

        return new BasicResponse(isCorrect, isCorrect ? "Correct answer!" : "Wrong answer." );
    }


    public void updateProgress(User user, boolean isCorrect, String activeCategory) {
        Progress progress = progressRepository.findByUser(user);
        if (progress == null) {
            throw new IllegalStateException("Progress not found for user: " + user.getUsername());
        }

        int currentLevel = progress.getCategoryProgress().getOrDefault(activeCategory, 1);
        int successStreak = progress.getCategorySuccessStreak().getOrDefault(activeCategory, 0);

        if (isCorrect) {
            successStreak++;
            if (successStreak >= 10) {
                progress.getCategoryProgress().put(activeCategory, currentLevel + 1);
                successStreak = 0;
            }
        } else {
            successStreak = 0;


            Map<String, Integer> weakPoints = progress.getWeakPoints();
            weakPoints.put(activeCategory, weakPoints.getOrDefault(activeCategory, 0) + 1);
            progress.setWeakPoints(weakPoints);

            /*
            double weaknessFactor = progress.getWeakPoints().getOrDefault(activeCategory, 0) / 10.0;
            if (Math.random() < weaknessFactor) {
                difficulty = Math.max(difficulty - 1, 1);
            }
             */


            if (currentLevel > 1) {
                progress.getCategoryProgress().put(activeCategory, currentLevel - 1);
            }
        }


        progress.getCategorySuccessStreak().put(activeCategory, successStreak);

        progressRepository.save(progress);
    }


    public void saveQuestionHistory(User user, Question question, boolean isCorrect) {
        QuestionHistory history = new QuestionHistory();
        history.setUser(user);
        history.setQuestion(question);
        history.setCorrect(isCorrect);
        history.setAnsweredAt(new Date());
        questionHistoryRepository.save(history);
    }
}

/*

    public void updateProgress(User user, boolean isCorrect, String activeCategory) {
        Progress progress = progressRepository.findByUser(user);
        if (progress == null) {
            throw new IllegalStateException("Progress not found for user: " + user.getUsername());
        }

        int currentLevel = progress.getCategoryProgress().getOrDefault(activeCategory, 1);
        int successStreak = progress.getCategorySuccessStreak().getOrDefault(activeCategory, 0);

        if (isCorrect) {
            successStreak++;
            if (successStreak >= 10) {
                progress.getCategoryProgress().put(activeCategory, currentLevel + 1);
                successStreak = 0; // איפוס הסטרייק לאחר העלאת הרמה
            }
        } else {
            successStreak = 0;

            // עדכון נקודות חולשה
            Map<String, Integer> weakPoints = progress.getWeakPoints();
            weakPoints.put(activeCategory, weakPoints.getOrDefault(activeCategory, 0) + 1);
            progress.setWeakPoints(weakPoints);

            // הורדת רמה
            if (currentLevel > 1) {
                progress.getCategoryProgress().put(activeCategory, currentLevel - 1);
            }
        }

        // עדכון הסטרייק עבור הקטגוריה
        progress.getCategorySuccessStreak().put(activeCategory, successStreak);

        progressRepository.save(progress);
    }
 */