package com.yuliialazarovych.keep_in_touch.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yuliialazarovych.keep_in_touch.dtos.CityDto;
import com.yuliialazarovych.keep_in_touch.services.CityService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/cities")
@CrossOrigin
public class CityController {

    private final CityService cityService;

    @Autowired
    public CityController(CityService cityService) {
        this.cityService = cityService;
    }


    @GetMapping
    public ResponseEntity<List<CityDto>> getAllCities() {
        List<CityDto> cities = cityService.getAllCityDto();
        return ResponseEntity.ok(cities);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CityDto> getCityById(@PathVariable Long id) {
        CityDto cityDto = cityService.getCityDto(id);
        return ResponseEntity.ok(cityDto);
    }


    @GetMapping("/search")
    public ResponseEntity<CityDto> getCityByName(@RequestParam String name) {
        CityDto cityDto = cityService.getCityByName(name);
        return ResponseEntity.ok(cityDto);
    }

}
