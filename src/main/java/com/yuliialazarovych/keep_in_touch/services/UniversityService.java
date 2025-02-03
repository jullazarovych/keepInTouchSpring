package com.yuliialazarovych.keep_in_touch.services;

import com.yuliialazarovych.keep_in_touch.domain.University;
import com.yuliialazarovych.keep_in_touch.dtos.UniversityDto;
import com.yuliialazarovych.keep_in_touch.repositories.UniversityRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UniversityService {

    private final UniversityRepository universityRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public UniversityService(UniversityRepository universityRepository, ModelMapper modelMapper) {
        this.universityRepository = universityRepository;
        this.modelMapper = modelMapper;
    }


    public University getUniversity(Long id) {
        return universityRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("University not found with ID: " + id));
    }


    public List<University> getAllUniversities() {
        return universityRepository.findAll();
    }


    @Transactional
    public UniversityDto getUniversityByName(String name) {
        University university = universityRepository.findByName(name)
                .orElseThrow(() -> new EntityNotFoundException("University not found with name: " + name));
        return modelMapper.map(university, UniversityDto.class);
    }

    @Transactional
    public List<UniversityDto> getAllUniversitiesDto() {
        List<University> universities = universityRepository.findAll();
        return universities.stream()
                .map(university -> modelMapper.map(university, UniversityDto.class))
                .collect(Collectors.toList());
    }


    @Transactional
    public UniversityDto getUniversityDto(Long id) {
        University university = getUniversity(id);
        return modelMapper.map(university, UniversityDto.class);
    }

}