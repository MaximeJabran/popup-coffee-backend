package com.maxprojects.coffeeapp.controllers;

import com.maxprojects.coffeeapp.models.Membership;
import com.maxprojects.coffeeapp.models.MembershipStatus;
import com.maxprojects.coffeeapp.repositories.MembershipRepository;
import com.maxprojects.coffeeapp.services.MembershipService;
import com.maxprojects.coffeeapp.services.OneTimeCodeService;
import com.maxprojects.coffeeapp.dto.MembershipRequest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;


import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/membership")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class MembershipController {

    private final MembershipService service;
    private final MembershipRepository membershipRepo;
    private final OneTimeCodeService otcService;   // ← REQUIRED

    @PostMapping
    public Membership create(@RequestBody MembershipRequest request) {
        return service.createMembership(request);
    }

    // Generate OTC for testing or staff use
    @PostMapping("/admin/generate-otc")
    public Map<String, String> generateOtc() {
        String code = String.format("%04d", (int)(Math.random() * 10000));
        otcService.storeCode(code);

        return Map.of("code", code);
    }

    @GetMapping("/all")
    public List<Membership> getAll() {
        return service.getAll();
    }

    @GetMapping("/pending")
    public List<Membership> getPending() {
        return membershipRepo.findByStatus(MembershipStatus.PENDING_APPROVAL);
    }

    @PostMapping("/{id}/approve")
    public Membership approve(@PathVariable Long id) {
        Membership m = membershipRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Not found"));

        m.setStatus(MembershipStatus.APPROVED);
        return membershipRepo.save(m);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMembership(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }


}
