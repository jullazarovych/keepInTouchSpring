package com.yuliialazarovych.keep_in_touch.repositories;

import com.yuliialazarovych.keep_in_touch.domain.SearchHistory;
import com.yuliialazarovych.keep_in_touch.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SearchHistoryRepository extends JpaRepository<SearchHistory, Long> {
    List<SearchHistory> findByUserOrderByTimestampDesc(User user);
}
