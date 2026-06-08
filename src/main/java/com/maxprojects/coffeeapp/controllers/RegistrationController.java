package com.maxprojects.coffeeapp.controllers;

import com.maxprojects.coffeeapp.models.Registration;
import com.maxprojects.coffeeapp.services.RegistrationService;
import com.maxprojects.coffeeapp.dto.RegistrationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;



@RestController
@RequestMapping("/registrations")
public class RegistrationController {

    @Autowired
    private RegistrationService registrationService;

    @PostMapping
    public Registration createRegistration(@Valid @RequestBody RegistrationRequest req) {
        return registrationService.register(
                req.getEventId(),
                req.getFirstName(),
                req.getLastName(),
                req.isSmsOptIn(),
                req.getPhone(),
                req.isEmailOptIn(),
                req.getEmail()
        );
    }


    @GetMapping
    public Iterable<Registration> getAllRegistrations() {
        return registrationService.getAll();
    }

    @GetMapping("/event/{eventId}")
    public List<Registration> getRegistrationsByEvent(@PathVariable Long eventId) {
        return registrationService.getRegistrationsByEvent(eventId);
    }


    @GetMapping("/{id}")
    public Registration getRegistration(@PathVariable Long id) {
        return registrationService.find(id);
    }

    @PatchMapping("/{id}/use")
    public Registration markAsUsed(@PathVariable Long id) {
        return registrationService.markAsUsed(id);
    }

    @PatchMapping("/{id}/arrived")
    public Registration toggleArrived(@PathVariable Long id) {
        return registrationService.toggleArrived(id);
    }

    @DeleteMapping("/{id}")
    public void deleteRegistration(@PathVariable Long id) {
        registrationService.delete(id);
    }


}
