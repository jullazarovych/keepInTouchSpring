package com.yuliialazarovych.keep_in_touch.services;

import com.yuliialazarovych.keep_in_touch.domain.Category;
import com.yuliialazarovych.keep_in_touch.domain.University;
import com.yuliialazarovych.keep_in_touch.dtos.EventDto;
import com.yuliialazarovych.keep_in_touch.domain.Event;
import com.yuliialazarovych.keep_in_touch.repositories.CategoryRepository;
import com.yuliialazarovych.keep_in_touch.repositories.EventRepository;
import com.yuliialazarovych.keep_in_touch.repositories.UniversityRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class EventService {

    private final EventRepository eventRepository;
    private final CategoryRepository categoryRepository;
    private final UniversityRepository universityRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public EventService(EventRepository eventRepository,
                        CategoryRepository categoryRepository,
                        UniversityRepository universityRepository,
                        ModelMapper modelMapper) {
        this.eventRepository = eventRepository;
        this.categoryRepository = categoryRepository;
        this.universityRepository = universityRepository;
        this.modelMapper = modelMapper;
    }

    public Event getEvent(Long id) {
        return eventRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Event not found"));
    }

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }
    @Transactional
    public EventDto getEventByName(String name) {
        Event event = eventRepository.findByName(name)
                .orElseThrow(() -> new EntityNotFoundException("Event not found with name: " + name));
        return modelMapper.map(event, EventDto.class);
    }
    @Transactional
    public List<EventDto> getAllEventsDto() {
        List<Event> events = eventRepository.findAll();
        return events.stream().map(event -> {
            EventDto dto = modelMapper.map(event, EventDto.class);
            dto.setCategoryIds(event.getCategories().stream()
                    .map(Category::getId)
                    .collect(Collectors.toSet()));
            if (event.getUniversity() != null) {
                University university = universityRepository.findById(event.getUniversity().getId())
                        .orElse(null);
            }
            return dto;
        }).collect(Collectors.toList());
    }

    @Transactional
    public EventDto getEventDto(Long id) {
        Event event = getEvent(id);
        EventDto dto = modelMapper.map(event, EventDto.class);

        dto.setCategoryIds(event.getCategories().stream()
                .map(Category::getId)
                .collect(Collectors.toSet()));

        if (event.getUniversity() != null) {
            University university = universityRepository.findById(event.getUniversity().getId())
                    .orElse(null);
             }

        return dto;
    }
    @Transactional
    public Set<String> getCategoryNamesForEvent(Long eventId) {
        Event event = getEvent(eventId);
        return event.getCategories().stream()
                .map(Category::getName)
                .collect(Collectors.toSet());
    }
    @Transactional
    public ResponseEntity<Void> deleteEvent(Long id) {
        if (eventRepository.existsById(id)) {
            eventRepository.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            throw new IllegalArgumentException("Event with ID " + id + " does not exist.");
        }
    }
    @Transactional
    public List<EventDto> searchEvents(String name, Long universityId, Long categoryId) {
        List<Event> events = eventRepository.findAll();
        if (name != null && !name.trim().isEmpty()) {
            events = events.stream()
                    .filter(e -> e.getName().toLowerCase().contains(name.toLowerCase()))
                    .collect(Collectors.toList());
        }

        if (universityId != null) {
            events = events.stream()
                    .filter(e -> e.getUniversity() != null && e.getUniversity().getId().equals(universityId))
                    .collect(Collectors.toList());
        }

        if (categoryId != null) {
            events = events.stream()
                    .filter(e -> e.getCategories().stream().anyMatch(c -> c.getId().equals(categoryId)))
                    .collect(Collectors.toList());
        }

        return events.stream()
                .map(event -> {
                    EventDto dto = modelMapper.map(event, EventDto.class);
                    dto.setCategoryIds(event.getCategories().stream().map(Category::getId).collect(Collectors.toSet()));
                    if (event.getUniversity() != null) {
                        dto.setUniversityName(event.getUniversity().getName());
                    }
                    return dto;
                })
                .collect(Collectors.toList());
    }
    @Transactional
    public ResponseEntity<EventDto> createEvent(EventDto eventDto) {
        Event event = modelMapper.map(eventDto, Event.class);
        University university = universityRepository.findById(eventDto.getUniversityId())
                .orElseThrow(() -> new IllegalArgumentException("University with ID " + eventDto.getUniversityId() + " does not exist."));
        event.setUniversity(university);

        Set<Category> categories = eventDto.getCategoryIds().stream()
                .map(categoryId -> categoryRepository.findById(categoryId)
                        .orElseThrow(() -> new IllegalArgumentException("Category with ID " + categoryId + " does not exist.")))
                .collect(Collectors.toSet());

        event.setCategories(categories);
        categories.forEach(category -> category.getEvents().add(event));
        eventRepository.save(event);

        EventDto createdEventDto = modelMapper.map(event, EventDto.class);
        createdEventDto.setCategoryIds(categories.stream().map(Category::getId).collect(Collectors.toSet()));

        return ResponseEntity.ok(createdEventDto);
    }
    @Transactional
    public ResponseEntity<EventDto> updateEvent(Long id, EventDto eventDto) {
        Event existingEvent = getEvent(id);

        University university = universityRepository.findById(eventDto.getUniversityId())
                .orElseThrow(() -> new IllegalArgumentException("University with ID " + eventDto.getUniversityId() + " does not exist."));
        existingEvent.setUniversity(university);

        Set<Category> categories = eventDto.getCategoryIds().stream()
                .map(categoryId -> categoryRepository.findById(categoryId)
                        .orElseThrow(() -> new IllegalArgumentException("Category with ID " + categoryId + " does not exist.")))
                .collect(Collectors.toSet());

        existingEvent.getCategories().clear();

        existingEvent.setCategories(categories);

        categories.forEach(category -> category.getEvents().add(existingEvent));

        existingEvent.setName(eventDto.getName());
        existingEvent.setDescription(eventDto.getDescription());
        existingEvent.setLocation(eventDto.getLocation());
        existingEvent.setImageUrl(eventDto.getImageUrl());
        existingEvent.setStartTime(eventDto.getStartTime());
        existingEvent.setEndTime(eventDto.getEndTime());
        existingEvent.setPrice(eventDto.getPrice());
        existingEvent.setContactInfo(eventDto.getContactInfo());
        existingEvent.setRegistrationLink(eventDto.getRegistrationLink());

        eventRepository.save(existingEvent);

        EventDto updatedEventDto = modelMapper.map(existingEvent, EventDto.class);
        updatedEventDto.setCategoryIds(categories.stream().map(Category::getId).collect(Collectors.toSet()));
        updatedEventDto.setUniversityId(university.getId());

        return ResponseEntity.ok(updatedEventDto);
    }
    @Transactional
    public List<EventDto> getLastEvents() {
         List<Event> lastEvents = eventRepository.findTop5ByOrderByStartTimeDesc();

        return lastEvents.stream()
                .map(event -> modelMapper.map(event, EventDto.class))
                .collect(Collectors.toList());
    }

}
