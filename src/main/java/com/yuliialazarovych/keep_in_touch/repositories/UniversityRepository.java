package com.yuliialazarovych.keep_in_touch.repositories;

import com.yuliialazarovych.keep_in_touch.domain.University;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UniversityRepository extends JpaRepository<University, Long> {
    List<University> findAll();
    Optional<University> findByName(String name);

}
