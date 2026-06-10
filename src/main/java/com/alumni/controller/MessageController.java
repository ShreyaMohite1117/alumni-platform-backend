package com.alumni.controller;

import com.alumni.entity.Message;
import com.alumni.entity.User;
import com.alumni.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/messages")
@CrossOrigin(origins = "*")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @PostMapping("/{receiverId}")
    public ResponseEntity<Message> sendMessage(@PathVariable Long receiverId,
                                                @RequestBody Map<String, String> body,
                                                Authentication authentication) {
        return ResponseEntity.ok(messageService.sendMessage(receiverId,
                body.get("content"), authentication.getName()));
    }

    @GetMapping("/conversation/{userId}")
    public ResponseEntity<List<Message>> getConversation(@PathVariable Long userId,
                                                          Authentication authentication) {
        return ResponseEntity.ok(messageService.getConversation(userId, authentication.getName()));
    }

    @GetMapping("/partners")
    public ResponseEntity<List<User>> getConversationPartners(Authentication authentication) {
        return ResponseEntity.ok(messageService.getConversationPartners(authentication.getName()));
    }

    @GetMapping("/unread-count")
    public ResponseEntity<Map<String, Long>> getUnreadCount(Authentication authentication) {
        long count = messageService.getUnreadCount(authentication.getName());
        return ResponseEntity.ok(Map.of("count", count));
    }
}
