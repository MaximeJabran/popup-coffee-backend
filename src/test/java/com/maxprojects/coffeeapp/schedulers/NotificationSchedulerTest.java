package com.maxprojects.coffeeapp.schedulers;

import com.maxprojects.coffeeapp.models.Registration;
import com.maxprojects.coffeeapp.repositories.RegistrationRepository;
import com.maxprojects.coffeeapp.schedulers.NotificationScheduler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class NotificationSchedulerTest {

    @Autowired
    private NotificationScheduler scheduler;

    @Autowired
    private RegistrationRepository repo;

    @BeforeEach
    void cleanDatabase() {
        repo.deleteAll();
    }

    @Test
    void testEmailNotificationForTomorrow() {
        Registration r = new Registration();
        r.setEmail("test@example.com");
        r.setEmailOptIn(true);
        r.setEventDate(LocalDate.now().plusDays(1));
        r.setEmailNotificationSent(false);
        repo.save(r);

        scheduler.sendNotifications();

        Registration updated = repo.findById(r.getId()).orElseThrow();
        assertTrue(updated.isEmailNotificationSent());
    }

    @Test
    void testSmsNotificationForToday() {
        Registration r = new Registration();
        r.setPhone("+32412345678");
        r.setSmsEligible(true);
        r.setEventDate(LocalDate.now());
        r.setSmsNotificationSent(false);
        repo.save(r);

        scheduler.sendNotifications();

        Registration updated = repo.findById(r.getId()).orElseThrow();
        assertTrue(updated.isSmsNotificationSent());
    }

    @Test
    void testNoNotificationIfFlagsAlreadyTrue() {
        Registration r = new Registration();
        r.setEmail("already@sent.com");
        r.setEmailOptIn(true);
        r.setEventDate(LocalDate.now().plusDays(1));
        r.setEmailNotificationSent(true); // already sent
        repo.save(r);

        scheduler.sendNotifications();

        Registration updated = repo.findById(r.getId()).orElseThrow();
        assertTrue(updated.isEmailNotificationSent()); // still true
    }

    @Test
    void testNoNotificationIfEventDateNull() {
        Registration r = new Registration();
        r.setEmail("null@date.com");
        r.setEmailOptIn(true);
        r.setEventDate(null);
        repo.save(r);

        scheduler.sendNotifications();

        Registration updated = repo.findById(r.getId()).orElseThrow();
        assertFalse(updated.isEmailNotificationSent());
        assertFalse(updated.isSmsNotificationSent());
    }
}
