package com.maxprojects.coffeeapp.services;

import com.maxprojects.coffeeapp.models.Event;
import com.maxprojects.coffeeapp.models.Registration;
import com.maxprojects.coffeeapp.repositories.EventRepository;
import com.maxprojects.coffeeapp.exceptions.EventNotFoundException;

import com.maxprojects.coffeeapp.repositories.RegistrationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegistrationService {

    @Autowired
    private RegistrationRepository registrationRepository;

    @Autowired
    private EventRepository eventRepository;

    public Registration register(Long eventId,
                                 String firstName,
                                 String lastName,
                                 boolean smsOptIn,
                                 String phone,
                                 boolean emailOptIn,
                                 String email) {

        // Normalize empty strings → null
        if (email != null && email.isBlank()) {
            email = null;
        }
        if (phone != null && phone.isBlank()) {
            phone = null;
        }

        Event event = eventRepository.findById(eventId).orElse(null);
        if (event == null) {
            throw new IllegalArgumentException("Event not found");
        }

        Registration r = new Registration();
        r.setEvent(event);

        // Convert LocalDateTime → LocalDate
        r.setEventDate(event.getStartDate().toLocalDate());

        r.setFirstName(firstName);
        r.setLastName(lastName);

        // EMAIL LOGIC
        if (emailOptIn) {
            if (!isValidEmail(email)) {
                throw new IllegalArgumentException("Invalid email format");
            }
            r.setEmail(email);
            r.setEmailOptIn(true);
        }

        // SMS LOGIC
        if (smsOptIn) {
            if (!isValidPhone(phone)) {
                throw new IllegalArgumentException("Invalid phone number");
            }

            r.setPhone(phone);
            r.setSmsOptIn(true);

            boolean eligible = phone.startsWith("+33") || phone.startsWith("+32");
            r.setSmsEligible(eligible);

            if (!eligible) {
                r.setSmsOptIn(false); // disable SMS if foreign number
            }
        }

        return registrationRepository.save(r);
    }

    private boolean isValidEmail(String email) {
        return email != null && email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
    }

    private boolean isValidPhone(String phone) {
        return phone != null && phone.matches("^\\+\\d{6,15}$");
    }

    public List<Registration> getRegistrationsByEvent(Long eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EventNotFoundException(eventId));

        return registrationRepository.findByEvent(event);
    }

    public Iterable<Registration> getAll() {
        return registrationRepository.findAll();
    }

    public Registration markAsUsed(Long id) {

        // 1. Load the registration from the database.
        //    If the ID does not exist, we stop immediately.
        Registration reg = registrationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Registration not found"));

        // 2. Prevent double-use.
        //    If the registration is already marked as used, we reject the request.
        if (reg.isUsed()) {
            throw new IllegalStateException("Registration already used");
        }

        // 3. Mark the registration as used.
        reg.setUsed(true);

        // 4. Save the updated registration back into the database.
        return registrationRepository.save(reg);
    }

    public Registration toggleArrived(Long id) {
        Registration reg = registrationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Registration not found"));

        reg.setArrived(!reg.isArrived());
        return registrationRepository.save(reg);
    }

    public Registration find(Long id) {
        return registrationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Registration not found"));
    }

    public void delete(Long id) {
        registrationRepository.deleteById(id);
    }

}
