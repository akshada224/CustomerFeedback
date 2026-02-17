package com.CustomerFeedback.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.CustomerFeedback.model.Feedback;
import com.CustomerFeedback.model.User;

@Repository
public interface feedbackRepository extends JpaRepository<Feedback, Long> {
    List<Feedback> findByUser(User user);
}