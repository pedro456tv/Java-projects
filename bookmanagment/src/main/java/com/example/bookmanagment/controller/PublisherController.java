package com.example.bookmanagment.controller;

import com.example.bookmanagment.model.Publisher;
import com.example.bookmanagment.service.PublisherService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/publishers")
public class PublisherController {

    private final PublisherService publisherService;

    public PublisherController(PublisherService publisherService) {
        this.publisherService = publisherService;
    }

    @GetMapping
    public String listPublishers(Model model) {
        model.addAttribute("publishers", publisherService.getAllPublishers());
        return "publishers/list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("publisher", new Publisher());
        return "publishers/form";
    }

    @PostMapping
    public String savePublisher(@Valid @ModelAttribute Publisher publisher, BindingResult result) {
        if (result.hasErrors()) return "publishers/form";
        publisherService.savePublisher(publisher);
        return "redirect:/publishers";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Publisher publisher = publisherService.getPublisherById(id).orElseThrow();
        model.addAttribute("publisher", publisher);
        return "publishers/form";
    }

    @GetMapping("/delete/{id}")
    public String deletePublisher(@PathVariable Long id) {
        publisherService.deletePublisher(id);
        return "redirect:/publishers";
    }
}
