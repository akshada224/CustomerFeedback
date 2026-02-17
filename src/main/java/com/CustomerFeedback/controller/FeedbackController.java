package com.CustomerFeedback.controller;

import java.security.Principal;
import java.time.LocalDate;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.CustomerFeedback.model.Feedback;
import com.CustomerFeedback.model.User;
import com.CustomerFeedback.service.FeedbackService;
import com.CustomerFeedback.service.UserService;

@Controller
@RequestMapping("/feedback")
public class FeedbackController {

    private final FeedbackService feedbackService;
    private final UserService userService;

    public FeedbackController(FeedbackService feedbackService, UserService userService) {
        this.feedbackService = feedbackService;
        this.userService = userService;
    }

    @GetMapping
    public String showFeedbackPage(Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }
        User user = userService.findByUsername(principal.getName());
        model.addAttribute("feedbacks", feedbackService.getFeedbacksByUser(user));
        model.addAttribute("feedback", new Feedback()); // For the 'Add New' form
        return "feedback";
    }

    @PostMapping
    public String saveFeedback(@ModelAttribute("feedback") Feedback feedback, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }
        User user = userService.findByUsername(principal.getName());
        feedback.setUser(user);
        feedback.setDate(LocalDate.now().toString());
        feedbackService.saveFeedback(feedback);
        return "redirect:/feedback?success";
    }

    @GetMapping("/edit/{id}")
    public String editFeedback(@PathVariable Long id, Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }
        User user = userService.findByUsername(principal.getName());
        Optional<Feedback> feedbackOpt = feedbackService.getFeedbackById(id);

        if (feedbackOpt.isPresent() && feedbackOpt.get().getUser().getId().equals(user.getId())) {
            model.addAttribute("feedbacks", feedbackService.getFeedbacksByUser(user));
            model.addAttribute("feedback", feedbackOpt.get());
            return "feedback";
        }
        return "redirect:/feedback";
    }
}