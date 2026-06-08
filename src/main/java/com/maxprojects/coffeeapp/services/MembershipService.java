package com.maxprojects.coffeeapp.services;

import com.maxprojects.coffeeapp.dto.MembershipRequest;
import com.maxprojects.coffeeapp.models.Membership;
import com.maxprojects.coffeeapp.models.MembershipStatus;
import com.maxprojects.coffeeapp.repositories.MembershipRepository;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
import com.maxprojects.coffeeapp.exceptions.InvalidOneTimeCodeException;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MembershipService {

    private final MembershipRepository membershipRepo;
    private final OneTimeCodeService otcService;

    // CREATE MEMBERSHIP (with OTC validation)
    public Membership createMembership(MembershipRequest request) {

        // Validate the OTC before creating the membership
        if (!otcService.isValid(request.getOneTimeCode())) {
            throw new InvalidOneTimeCodeException();
        }

        Membership m = Membership.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .birthDate(request.getBirthDate())
                .email(request.getEmail())
                .phone(request.getPhone())
                .receiveEmail(request.isReceiveEmail())
                .receiveSMS(request.isReceiveSMS())
                .status(MembershipStatus.PENDING_APPROVAL)
                .build();

        Membership saved = membershipRepo.save(m);

        // Consume the code AFTER successful save
        otcService.consume(request.getOneTimeCode());

        return saved;
    }

    // LIST ALL MEMBERSHIPS
    public List<Membership> getAll() {
        return membershipRepo.findAll();
    }

    public void delete(Long id) {
        membershipRepo.deleteById(id);
    }

}
