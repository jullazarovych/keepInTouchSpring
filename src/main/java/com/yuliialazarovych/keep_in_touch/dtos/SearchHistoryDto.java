package com.yuliialazarovych.keep_in_touch.dtos;


import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SearchHistoryDto {
    private String query;
    private LocalDateTime timestamp;
}

