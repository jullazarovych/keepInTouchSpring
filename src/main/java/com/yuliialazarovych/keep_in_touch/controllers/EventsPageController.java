package com.yuliialazarovych.keep_in_touch.controllers;

import com.yuliialazarovych.keep_in_touch.dtos.EventDto;
import com.yuliialazarovych.keep_in_touch.services.CategoryService;
import com.yuliialazarovych.keep_in_touch.services.EventService;
import com.yuliialazarovych.keep_in_touch.services.UniversityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@Controller
public class EventsPageController {

    private final EventService eventService;
    private final UniversityService universityService;
    private final CategoryService categoryService;

    @Autowired
    public EventsPageController(EventService eventService, UniversityService universityService, CategoryService categoryService) {
        this.eventService = eventService;
        this.universityService = universityService;
        this.categoryService = categoryService;
    }

    @GetMapping("/events")
    public String getAllEventsPage(
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "universityId", required = false) Long universityId,
            @RequestParam(name = "categoryId", required = false) Long categoryId,
            Model model) {

        // Завантажуємо університети та категорії для фільтра
        model.addAttribute("universities", universityService.getAllUniversities());
        model.addAttribute("categories", categoryService.getAllCategories());

        List<EventDto> events = eventService.searchEvents(name, universityId, categoryId);
        model.addAttribute("events", events);

        return "events";
    }

    @GetMapping("/events/{id}")
    public String getEventDetailsPage(@PathVariable Long id, Model model) {
        EventDto event = eventService.getEventDto(id);
        Set<String> categoryNames = eventService.getCategoryNamesForEvent(id);
        model.addAttribute("event", event);
        model.addAttribute("categoryNames", categoryNames);
        return "event-details";
    }

    @GetMapping("/events/create")
    public String showCreateEventForm(Model model) {
        model.addAttribute("event", new EventDto());
        model.addAttribute("universities", universityService.getAllUniversities());
        model.addAttribute("categories", categoryService.getAllCategories());
        return "event-form";
    }

    @PostMapping("/events/create")
    public String createEvent(@ModelAttribute("event") EventDto eventDto) {
        eventService.createEvent(eventDto);
        return "redirect:/events";
    }

    @GetMapping("/events/edit/{id}")
    public String showEditEventForm(@PathVariable Long id, Model model) {
        EventDto eventDto = eventService.getEventDto(id);
        model.addAttribute("event", eventDto);
        model.addAttribute("universities", universityService.getAllUniversities());
        model.addAttribute("categories", categoryService.getAllCategories());
        return "event-form";
    }

    @PostMapping("/events/edit/{id}")
    public String updateEvent(@PathVariable Long id, @ModelAttribute("event") EventDto eventDto) {
        eventService.updateEvent(id, eventDto);
        return "redirect:/events";
    }

    @PostMapping("/events/delete/{id}")
    public String deleteEvent(@PathVariable Long id) {
        eventService.deleteEvent(id);
        return "redirect:/events";
    }

}
