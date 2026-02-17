package com.CustomerFeedback.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.CustomerFeedback.service.FeedbackService;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final FeedbackService feedbackService;

    public AdminController(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("feedbacks", feedbackService.getAllFeedbacks());
        return "admin_dashboard";
    }

    @GetMapping("/feedback/edit/{id}")
    public String editFeedbackForm(@PathVariable Long id, Model model) {
        model.addAttribute("feedback", feedbackService.getFeedbackById(id).orElse(null));
        return "admin_edit_feedback";
    }

    @PostMapping("/feedback/update")
    public String updateFeedback(@ModelAttribute("feedback") com.CustomerFeedback.model.Feedback feedback) {
        com.CustomerFeedback.model.Feedback existingFeedback = feedbackService.getFeedbackById(feedback.getId())
                .orElse(null);
        if (existingFeedback != null) {
            existingFeedback.setMessage(feedback.getMessage());
            feedbackService.saveFeedback(existingFeedback);
        }
        return "redirect:/admin/dashboard";
    }

    @GetMapping("/feedback/view/{id}")
    public String viewFeedback(@PathVariable Long id, Model model) {
        model.addAttribute("feedback", feedbackService.getFeedbackById(id).orElse(null));
        return "admin_view_feedback";
    }

    @GetMapping("/feedback/delete/{id}")
    public String deleteFeedback(@PathVariable Long id) {
        feedbackService.deleteFeedbackById(id);
        return "redirect:/admin/dashboard";
    }
}