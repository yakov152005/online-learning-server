package org.server.onlinelearningserver.services.generator;

import org.server.onlinelearningserver.entitys.Question;

public interface QuestionGeneratorStrategy {
    Question generateQuestion(int difficulty);
}