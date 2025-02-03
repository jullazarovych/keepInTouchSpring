package com.yuliialazarovych.keep_in_touch.controllers;

import com.yuliialazarovych.keep_in_touch.dtos.EventDto;
import com.yuliialazarovych.keep_in_touch.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


import java.util.List;

@Controller
public class HomeController {

    private final EventService eventService;

    @Autowired
    public HomeController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping("/")
    public String home(Model model) {
        List<EventDto> latestEvents = eventService.getLastEvents(); // Приклад методу з вашого EventService
        model.addAttribute("latestEvents", latestEvents);
        return "home";
    }
}
