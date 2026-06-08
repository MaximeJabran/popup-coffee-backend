package com.maxprojects.coffeeapp.controllers;

import com.maxprojects.coffeeapp.models.Registration;
import com.maxprojects.coffeeapp.repositories.RegistrationRepository;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/admin/notifications")
public class AdminNotificationController {

    private final RegistrationRepository registrationRepository;

    public AdminNotificationController(RegistrationRepository registrationRepository) {
        this.registrationRepository = registrationRepository;
    }

    @GetMapping("/upcoming")
    public List<Registration> getUpcomingNotifications() {

        LocalDate today = LocalDate.now();
        LocalDate tomorrow = today.plusDays(1);

        return registrationRepository.findAll().stream()
                .filter(r -> r.getEventDate() != null)
                .filter(r ->
                        (r.getEventDate().equals(tomorrow) && r.isEmailOptIn()) ||
                                (r.getEventDate().equals(today) && r.isSmsEligible())
                )
                .toList();
    }

    @GetMapping("/dry-run")
    public List<String> dryRun() {

        LocalDate today = LocalDate.now();
        LocalDate tomorrow = today.plusDays(1);

        return registrationRepository.findAll().stream()
                .filter(r -> r.getEventDate() != null)
                .map(r -> {
                    if (r.getEventDate().equals(tomorrow) && r.isEmailOptIn()) {
                        return "EMAIL → " + r.getEmail();
                    }
                    if (r.getEventDate().equals(today) && r.isSmsEligible()) {
                        return "SMS → " + r.getPhone();
                    }
                    return null;
                })
                .filter(msg -> msg != null)
                .toList();
    }

}
