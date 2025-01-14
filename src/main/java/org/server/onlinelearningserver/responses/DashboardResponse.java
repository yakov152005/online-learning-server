package org.server.onlinelearningserver.responses;

import lombok.Getter;
import lombok.Setter;
import org.server.onlinelearningserver.dtos.CategoryProgressDto;
import org.server.onlinelearningserver.dtos.CategorySuccessStreakDto;
import org.server.onlinelearningserver.dtos.QuestionDto;
import org.server.onlinelearningserver.dtos.WeakPointDto;

import java.util.List;
import java.util.Map;

@Setter
@Getter
public class DashboardResponse extends BasicResponse {
    private List<CategorySuccessStreakDto> successStreak;
    private List<QuestionDto> openQuestions;
    private List<WeakPointDto> weakPoints;
    private List<CategoryProgressDto> currentLevels;
    private Map<String, Integer> correctAnswersPerCategory;
    private Map<String, Integer> incorrectAnswersPerCategory;
    Map<String, Double> successRate;
    private int totalCorrectAnswers;
    private int totalIncorrectAnswers;
    private int totalUnansweredQuestion;
    private double totalSuccessRate;

    public DashboardResponse(boolean success, String error, List<CategorySuccessStreakDto> successStreak,
                             List<QuestionDto> openQuestions, List<WeakPointDto> weakPoints,
                             List<CategoryProgressDto> currentLevels, Map<String, Integer> correctAnswersPerCategory,
                             Map<String, Integer> incorrectAnswersPerCategory, Map<String, Double> successRate,
                             int totalCorrectAnswers, int totalIncorrectAnswers,int totalUnansweredQuestion,double totalSuccessRate) {
        super(success, error);
        this.successStreak = successStreak;
        this.openQuestions = openQuestions;
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

    public DashboardResponse(boolean success, String error) {
        super(success, error);
    }

}
