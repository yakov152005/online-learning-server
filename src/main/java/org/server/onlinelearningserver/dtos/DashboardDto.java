package org.server.onlinelearningserver.dtos;


import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class DashboardDto {
    private List<CategorySuccessStreakDto> successStreak;
    private List<QuestionDto> openQuestions;
    private List<QuestionDto> questionsAnsweredIncorrectly;
    private List<WeakPointDto> weakPoints;
    private List<CategoryProgressDto> currentLevels;
    private Map<String, Integer> correctAnswersPerCategory;
    private Map<String, Integer> incorrectAnswersPerCategory;
    private Map<String, Double> successRate;
    private int totalCorrectAnswers;
    private int totalIncorrectAnswers;
    private int totalUnansweredQuestion;
    private double totalSuccessRate;

    public DashboardDto(List<CategorySuccessStreakDto> successStreak, List<QuestionDto> openQuestions, List<QuestionDto> questionsAnsweredIncorrectly, List<WeakPointDto> weakPoints, List<CategoryProgressDto> currentLevels, Map<String, Integer> correctAnswersPerCategory, Map<String, Integer> incorrectAnswersPerCategory, Map<String, Double> successRate, int totalCorrectAnswers, int totalIncorrectAnswers, int totalUnansweredQuestion, double totalSuccessRate) {
        this.successStreak = successStreak;
        this.openQuestions = openQuestions;
        this.questionsAnsweredIncorrectly = questionsAnsweredIncorrectly;
        this.weakPoints = weakPoints;
        this.currentLevels = currentLevels;
        this.correctAnswersPerCategory = correctAnswersPerCategory;
        this.incorrectAnswersPerCategory = incorrectAnswersPerCategory;
        this.successRate = successRate;
        this.totalCorrectAnswers = totalCorrectAnswers;
        this.totalIncorrectAnswers = totalIncorrectAnswers;
        this.totalUnansweredQuestion = totalUnansweredQuestion;
        this.totalSuccessRate = totalSuccessRate;
    }
}
