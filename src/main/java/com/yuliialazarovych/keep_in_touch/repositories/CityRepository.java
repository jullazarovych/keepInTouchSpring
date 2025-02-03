package com.yuliialazarovych.keep_in_touch.repositories;

import com.yuliialazarovych.keep_in_touch.domain.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
public interface CityRepository  extends JpaRepository<City, Long> {
    List<City> findAll();

    boolean existsById(Long id);

    void deleteById(Long id);

    Optional<City> findByName(String name);

}
