package com.yuliialazarovych.keep_in_touch.controllers;
import com.yuliialazarovych.keep_in_touch.domain.SearchHistory;
import com.yuliialazarovych.keep_in_touch.domain.User;
import com.yuliialazarovych.keep_in_touch.dtos.SearchHistoryDto;
import com.yuliialazarovych.keep_in_touch.services.SearchHistoryService;
import com.yuliialazarovych.keep_in_touch.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/search-history")
public class SearchHistoryController {

    private final SearchHistoryService searchHistoryService;
    private final UserService userService;

    @Autowired
    public SearchHistoryController(SearchHistoryService searchHistoryService, UserService userService) {
        this.searchHistoryService = searchHistoryService;
        this.userService = userService;
    }

    @PostMapping("/search")
    public ResponseEntity<String> searchAndSaveQuery(@RequestParam String query, @RequestParam Long userId) {

        searchHistoryService.saveSearchQuery(userId, query);
        String searchResult = performSearch(query);
        return ResponseEntity.ok("Search result for query: '" + query + "': " + searchResult);
    }

    private String performSearch(String query) {
        return "Found some results matching '" + query + "'";
    }

    @GetMapping("/{userId}")
    public List<SearchHistoryDto> getSearchHistory(@PathVariable Long userId) {
        User user = userService.getUser(userId);
        List<SearchHistory> searchHistoryList = searchHistoryService.getSearchHistory(user);

        return searchHistoryList.stream()
                .map(history -> SearchHistoryDto.builder()
                        .query(history.getQuery())
                        .timestamp(history.getTimestamp())
                        .build())
                .collect(Collectors.toList());
    }
}
