package com.maxprojects.coffeeapp.repositories;

import com.maxprojects.coffeeapp.models.Membership;
import com.maxprojects.coffeeapp.models.MembershipStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MembershipRepository extends JpaRepository<Membership, Long> {

    List<Membership> findByStatus(MembershipStatus status);
}
