package com.yuliialazarovych.keep_in_touch.services;

import com.yuliialazarovych.keep_in_touch.domain.City;
import com.yuliialazarovych.keep_in_touch.dtos.CityDto;
import com.yuliialazarovych.keep_in_touch.repositories.CityRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class CityService {

        private final CityRepository cityRepository;
        private final ModelMapper modelMapper;

        @Autowired
        public CityService(CityRepository cityRepository, ModelMapper modelMapper) {
            this.cityRepository = cityRepository;
            this.modelMapper = modelMapper;
        }

        public City getCity(Long id) {
            return cityRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("City not found"));
        }

        public List<City> getAllCity() {
            return cityRepository.findAll();
        }
        @Transactional
        public CityDto getCityByName(String name) {
            City city = cityRepository.findByName(name)
                .orElseThrow(() -> new EntityNotFoundException("City not found with name: " + name));
            return modelMapper.map(city, CityDto.class);
        }
        @Transactional
        public List<CityDto> getAllCityDto() {
            List<City> cities = cityRepository.findAll();
            return cities.stream()
                    .map(city -> modelMapper.map(city, CityDto.class))
                    .collect(Collectors.toList());
        }

        @Transactional
        public CityDto getCityDto(Long id) {
            City city = getCity(id);
            return modelMapper.map(city, CityDto.class);
        }
    }
