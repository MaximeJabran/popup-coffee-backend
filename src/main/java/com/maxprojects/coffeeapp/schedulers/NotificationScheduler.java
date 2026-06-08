package com.maxprojects.coffeeapp.schedulers;

import com.maxprojects.coffeeapp.models.Registration;
import com.maxprojects.coffeeapp.repositories.RegistrationRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;

@Component
public class NotificationScheduler {

    private static final Logger log = LoggerFactory.getLogger(NotificationScheduler.class);

    private final RegistrationRepository registrationRepository;

    public NotificationScheduler(RegistrationRepository registrationRepository) {
        this.registrationRepository = registrationRepository;
    }

    // Runs every day at 10:00
    @Scheduled(cron = "0 0 10 * * *")
    public void sendNotifications() {

        LocalDate today = LocalDate.now();
        LocalDate tomorrow = today.plusDays(1);

        Iterable<Registration> all = registrationRepository.findAll();

        for (Registration r : all) {

            LocalDate eventDate = r.getEventDate();
            if (eventDate == null) continue;

            // EMAIL NOTIFICATION — 1 day before
            if (eventDate.equals(tomorrow)
                    && r.isEmailOptIn()
                    && !r.isEmailNotificationSent()) {

                log.info("Sending EMAIL notification to {}", r.getEmail());

                r.setEmailNotificationSent(true);
                registrationRepository.save(r);
            }

            // SMS NOTIFICATION — day of event
            if (eventDate.equals(today)
                    && r.isSmsEligible()
                    && !r.isSmsNotificationSent()) {

                log.info("Sending SMS notification to {}", r.getPhone());


                r.setSmsNotificationSent(true);
                registrationRepository.save(r);
            }
        }
    }
}
