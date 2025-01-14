package org.server.onlinelearningserver.controllers;

import org.server.onlinelearningserver.entitys.User;
import org.server.onlinelearningserver.responses.BasicResponse;
import org.server.onlinelearningserver.responses.DashboardResponse;
import org.server.onlinelearningserver.responses.QuestionResponse;
import org.server.onlinelearningserver.responses.SubmitResponse;
import org.server.onlinelearningserver.services.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static org.server.onlinelearningserver.utils.Constants.UrlClient.URL_SERVER;

@RestController
@RequestMapping(URL_SERVER)
public class QuestionController {

    private final QuestionService questionService;

    @Autowired
    public QuestionController(QuestionService questionService){
        this.questionService = questionService;
    }

    @GetMapping("/get-question")
    public QuestionResponse generateQuestion(@RequestParam String username, @RequestParam String category){
        return questionService.generateQuestion(username,category);
    }

    @PostMapping("/submit-answer")
    public SubmitResponse submitAnswer(@RequestParam long questionId,
                                       @RequestParam String userAnswer,
                                       @RequestParam String username){
        return questionService.submitAnswer(questionId,userAnswer,username);
    }

    @GetMapping("/get-dashboard-user")
    private DashboardResponse getDashboardDetails(@RequestParam String username){
       return questionService.getDashboard(username);
    }

}
