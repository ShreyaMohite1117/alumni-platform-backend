package com.alumni.service;

import com.alumni.entity.MentorshipRequest;
import com.alumni.entity.User;
import com.alumni.repository.MentorshipRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MentorshipService {

    @Autowired private MentorshipRequestRepository mentorshipRepository;
    @Autowired private UserService userService;

    public MentorshipRequest sendRequest(Long mentorId, String message, String topics, String email) {
        User mentee = userService.getCurrentUser(email);
        User mentor = userService.getUserById(mentorId);

        MentorshipRequest request = new MentorshipRequest();
        request.setMentee(mentee);
        request.setMentor(mentor);
        request.setMessage(message);
        request.setTopics(topics);
        return mentorshipRepository.save(request);
    }

    public List<MentorshipRequest> getMySentRequests(String email) {
        User user = userService.getCurrentUser(email);
        return mentorshipRepository.findByMentee(user);
    }

    public List<MentorshipRequest> getMyReceivedRequests(String email) {
        User user = userService.getCurrentUser(email);
        return mentorshipRepository.findByMentor(user);
    }

    public MentorshipRequest updateStatus(Long requestId, MentorshipRequest.Status status, String email) {
        MentorshipRequest request = mentorshipRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Request not found"));
        User user = userService.getCurrentUser(email);
        if (!request.getMentor().getId().equals(user.getId())) {
            throw new RuntimeException("Not authorized");
        }
        request.setStatus(status);
        return mentorshipRepository.save(request);
    }
}
