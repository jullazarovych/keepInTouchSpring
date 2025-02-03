package com.yuliialazarovych.keep_in_touch.controllers;

import com.yuliialazarovych.keep_in_touch.dtos.UniversityDto;
import com.yuliialazarovych.keep_in_touch.services.UniversityService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/universities")
@CrossOrigin
public class UniversityController {

    private final UniversityService universityService;

    @Autowired
    public UniversityController(UniversityService universityService) {
        this.universityService = universityService;
    }

    @GetMapping
    public ResponseEntity<List<UniversityDto>> getAllUniversities() {
        List<UniversityDto> universities = universityService.getAllUniversitiesDto();
        return ResponseEntity.ok(universities);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UniversityDto> getUniversityById(@PathVariable Long id) {
        UniversityDto universityDto = universityService.getUniversityDto(id);
        return ResponseEntity.ok(universityDto);
    }

    @GetMapping("/search")
    public ResponseEntity<UniversityDto> getUniversityByName(@RequestParam String name) {
        UniversityDto universityDto = universityService.getUniversityByName(name);
        return ResponseEntity.ok(universityDto);
    }

}
