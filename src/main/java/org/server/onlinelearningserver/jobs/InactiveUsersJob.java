package org.server.onlinelearningserver.jobs;

import org.server.onlinelearningserver.entitys.User;
import org.server.onlinelearningserver.repositoris.SessionRepository;
import org.server.onlinelearningserver.repositoris.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.server.onlinelearningserver.utils.ApiEmailProcessor.sendEmail;
import static org.server.onlinelearningserver.utils.Constants.Mail.CONTENT;
import static org.server.onlinelearningserver.utils.Constants.Mail.TITLE;


@Component
public class InactiveUsersJob {
    private final UserRepository userRepository;


    @Autowired
    public InactiveUsersJob(UserRepository userRepository,SessionRepository sessionRepository){
        this.userRepository = userRepository;
    }


    @Scheduled(cron = "0 43 23 * * *")
    public void sendMailToNotLoggedUsers(){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, -7);
        Date lastWeek = calendar.getTime();
        Date temp = new Date();
        List<User> loginActivityList = userRepository.findUsersNotLoggedInLastWeek(lastWeek);
        if (loginActivityList != null){
            List<String> emails = loginActivityList.stream().map(User::getEmail).toList();
            for (String email : emails){
                System.out.println(email);
                sendEmail(email, TITLE, CONTENT);
            }
        }
    }
}
