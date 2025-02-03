package org.server.onlinelearningserver.jobs;
import org.server.onlinelearningserver.entitys.User;
import org.server.onlinelearningserver.entitys.WeeklyStats;
import org.server.onlinelearningserver.repositoris.UserRepository;
import org.server.onlinelearningserver.repositoris.WeeklyStatsRepository;
import org.server.onlinelearningserver.responses.DashboardResponse;
import org.server.onlinelearningserver.services.QuestionService;
import org.server.onlinelearningserver.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.server.onlinelearningserver.utils.ApiEmailProcessor.sendEmail;


@Component
public class WeeklyProgressJob {
    private final UserRepository userRepository;
    private final WeeklyStatsRepository weeklyStatsRepository;

    @Autowired
    public WeeklyProgressJob(UserRepository userRepository, WeeklyStatsRepository weeklyStatsRepository) {
        this.userRepository = userRepository;
        this.weeklyStatsRepository = weeklyStatsRepository;
    }

    /**
    0 0 12 * * SUN
    ↓ ↓ ↓  ↓  ↓  ↓
    | | |  |  |  └── יום בשבוע (SUN = יום ראשון)
    | | |  |  └──── חודש (כל חודש, *)
    | | |  └────── יום בחודש (כל יום, *)
    | | └──────── שעה (12:00)
    | └────────── דקות (00)
    └──────────── שניות (00)
     **/
    @Scheduled(cron = "0 0 12 * * *")
    public void sendWeeklyProgressReport() {
        System.out.println("Check check");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, -7);
        Date oneWeekAgo = calendar.getTime();

        List<User> users = userRepository.findAll();
        for (User user : users) {
            List<WeeklyStats> statsList = weeklyStatsRepository.findLatestByUser(user);

            if (statsList.isEmpty()) {
                System.err.println("⚠️ אין נתונים קודמים עבור המשתמש: " + user.getUsername() + ". הדוח השבועי לא ישלח.");
                continue;
            }

            WeeklyStats lastWeekStats = statsList.getFirst();
            if (lastWeekStats.getRecordedAt().after(oneWeekAgo)) {
                System.out.println("⏳ טרם חלף שבוע מאז השמירה האחרונה עבור המשתמש: " + user.getUsername());
                continue;
            }


            double currentSuccessRate = lastWeekStats.getCurrentTotalSuccessRate();
            double previousSuccessRate = lastWeekStats.getPreviousTotalSuccessRate();
            DecimalFormat df = new DecimalFormat("#.##");
            double progress = currentSuccessRate - previousSuccessRate;

            String progressMessage = (progress >= 0)
                    ? "שיפרת את אחוז ההצלחה שלך ב-" + df.format(progress) + "%"
                    : "אחוז ההצלחה שלך ירד ב-" + df.format(-progress) + "%";

            String emailContent = "שלום " + user.getUsername() + ",\n\n"
                                  + "הנה דוח ההתקדמות השבועי שלך:\n"
                                  + "אחוז הצלחה נוכחי: " + df.format(currentSuccessRate) + "%\n"
                                  + "אחוז הצלחה שבוע קודם: " + df.format(previousSuccessRate) + "%\n"
                                  + progressMessage + "\n\n"
                                  + "בהצלחה בהמשך הלמידה!";

            sendEmail(user.getEmail(), "דוח ההתקדמות השבועי שלך", emailContent);
            System.out.println("📩 דוח נשלח למשתמש: " + user.getUsername());
        }
    }
}

