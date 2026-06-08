package com.maxprojects.coffeeapp.services;

import com.maxprojects.coffeeapp.models.Registration;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    public void sendQrEmail(Registration registration) {
        // TODO: replace with real email sending logic

        System.out.println("Pretending to send email to: " + registration.getEmail());
    }
}
