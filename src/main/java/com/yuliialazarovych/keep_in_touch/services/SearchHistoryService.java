package com.yuliialazarovych.keep_in_touch.services;

import com.yuliialazarovych.keep_in_touch.domain.SearchHistory;
import com.yuliialazarovych.keep_in_touch.domain.User;
import com.yuliialazarovych.keep_in_touch.repositories.SearchHistoryRepository;
import com.yuliialazarovych.keep_in_touch.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SearchHistoryService {

    private final SearchHistoryRepository searchHistoryRepository;
    private final UserRepository userRepository;

    @Autowired
    public SearchHistoryService(SearchHistoryRepository searchHistoryRepository, UserRepository userRepository) {
        this.searchHistoryRepository = searchHistoryRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public SearchHistory saveSearchQuery(Long userId, String query) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with ID: " + userId));

        SearchHistory searchHistory = SearchHistory.builder()
                .user(user)
                .query(query)
                .timestamp(LocalDateTime.now())
                .build();

        return searchHistoryRepository.save(searchHistory);
    }

    public List<SearchHistory> getSearchHistory(User user) {
        return searchHistoryRepository.findByUserOrderByTimestampDesc(user);
    }
}
