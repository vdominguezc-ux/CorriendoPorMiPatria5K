package com.nuevafrecuencia.patria5kweb.controller;

import com.nuevafrecuencia.patria5kweb.model.Participant;
import com.nuevafrecuencia.patria5kweb.service.ParticipantService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PublicController {
    private final ParticipantService service;

    public PublicController(ParticipantService service) {
        this.service = service;
    }

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/inscripcion")
    public String registration(Model model) {
        model.addAttribute("participant", new Participant());
        return "register";
    }

    @PostMapping("/inscripcion")
    public String register(@Valid @ModelAttribute("participant") Participant participant,
                           BindingResult bindingResult,
                           Model model) {
        if (bindingResult.hasErrors()) return "register";
        participant.setName(participant.getName().trim());
        participant.setMunicipality(participant.getMunicipality().trim());
        participant.setPhone(participant.getPhone().trim());
        Participant saved = service.register(participant);
        model.addAttribute("participant", saved);
        return "confirmation";
    }

    @GetMapping("/patrocinadores")
    public String sponsors() {
        return "sponsors";
    }
}
