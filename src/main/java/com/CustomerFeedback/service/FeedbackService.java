package com.CustomerFeedback.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.CustomerFeedback.model.Feedback;
import com.CustomerFeedback.repository.feedbackRepository;

import com.CustomerFeedback.model.User;

@Service
@Transactional
public class FeedbackService {

	private final feedbackRepository feedbackRepository;

	public FeedbackService(feedbackRepository feedbackRepository) {
		this.feedbackRepository = feedbackRepository;
	}

	public List<Feedback> getAllFeedbacks() {
		return feedbackRepository.findAll();
	}

	public List<Feedback> getFeedbacksByUser(User user) {
		return feedbackRepository.findByUser(user);
	}

	public Optional<Feedback> getFeedbackById(Long id) {
		return feedbackRepository.findById(id);
	}

	public Feedback saveFeedback(Feedback feedback) {
		return feedbackRepository.save(feedback);
	}

	public void deleteFeedbackById(Long id) {
		feedbackRepository.deleteById(id);
	}
}