package com.maxprojects.coffeeapp.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class MembershipRequest {

    private String firstName;
    private String lastName;
    private LocalDate birthDate;

    private boolean receiveEmail;
    private boolean receiveSMS;

    private String email;
    private String phone;

    private String oneTimeCode;
}
