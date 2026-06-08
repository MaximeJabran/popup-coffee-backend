package com.maxprojects.coffeeapp.repositories;

import com.maxprojects.coffeeapp.models.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    List<Event> findByStartDateAfter(LocalDateTime now);

    Optional<Event> findFirstByStartDateAfterOrderByStartDateAsc(LocalDateTime now);


}
