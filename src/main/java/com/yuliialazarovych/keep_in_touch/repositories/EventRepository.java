package com.yuliialazarovych.keep_in_touch.repositories;

import com.yuliialazarovych.keep_in_touch.domain.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findAll();
    Optional<Event> findById(Long id);
    void deleteById(Long id);
    boolean existsById(Long id);
    Optional<Event> findByName(String name);
    List<Event> findTop5ByOrderByStartTimeDesc();
}
