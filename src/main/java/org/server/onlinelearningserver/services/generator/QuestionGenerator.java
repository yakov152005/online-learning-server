package org.server.onlinelearningserver.services.generator;

import org.server.onlinelearningserver.entitys.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Map;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class QuestionGenerator {

    private final Map<String, QuestionGeneratorStrategy> questionStrategies;

    @Autowired
    public QuestionGenerator(List<QuestionGeneratorStrategy> strategies) {
        this.questionStrategies = strategies.stream()
                .collect(Collectors.toMap(s -> s.getClass().getSimpleName()
                        .replace("QuestionGenerator", "").toLowerCase(), s -> s));
        for (Map.Entry<String,QuestionGeneratorStrategy> entry : questionStrategies.entrySet()){
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }

    public Question generateQuestion(String category, int difficulty) {
        QuestionGeneratorStrategy generator = questionStrategies.get(category.toLowerCase());

        if (generator == null) {
            throw new IllegalArgumentException("Unknown question type: " + category);
        }
        System.out.println(generator.getClass().getSimpleName());
        return generator.generateQuestion(difficulty);
    }
}
