package com.alumni.controller;

import com.alumni.entity.MentorshipRequest;
import com.alumni.service.MentorshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/mentorship")
@CrossOrigin(origins = "*")
public class MentorshipController {

    @Autowired
    private MentorshipService mentorshipService;

    @PostMapping("/request/{mentorId}")
    public ResponseEntity<MentorshipRequest> sendRequest(
            @PathVariable Long mentorId,
            @RequestBody Map<String, String> body,
            Authentication authentication) {
        return ResponseEntity.ok(mentorshipService.sendRequest(
                mentorId, body.get("message"), body.get("topics"), authentication.getName()
        ));
    }

    @GetMapping("/sent")
    public ResponseEntity<List<MentorshipRequest>> getSentRequests(Authentication authentication) {
        return ResponseEntity.ok(mentorshipService.getMySentRequests(authentication.getName()));
    }

    @GetMapping("/received")
    public ResponseEntity<List<MentorshipRequest>> getReceivedRequests(Authentication authentication) {
        return ResponseEntity.ok(mentorshipService.getMyReceivedRequests(authentication.getName()));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<MentorshipRequest> updateStatus(
            @PathVariable Long id,
            @RequestBody Map<String, String> body,
            Authentication authentication) {
        MentorshipRequest.Status status = MentorshipRequest.Status.valueOf(body.get("status"));
        return ResponseEntity.ok(mentorshipService.updateStatus(id, status, authentication.getName()));
    }
}
