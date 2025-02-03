package com.yuliialazarovych.keep_in_touch.dtos;

import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EventDto {

    private Long id;
    private String name;
    private Long universityId;
    private Set<Long> categoryIds= new HashSet<>();
    private String description;
    private String imageUrl;
    private String location;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private BigDecimal price;
    private String contactInfo;
    private String registrationLink;
    private Set<String> categoryNames;

    private String universityName;
}
