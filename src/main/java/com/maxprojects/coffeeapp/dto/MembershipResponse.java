package com.maxprojects.coffeeapp.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MembershipResponse {
    private String firstName;
    private String membershipNumber;
    private String message;
}
