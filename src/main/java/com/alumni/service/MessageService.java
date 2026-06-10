package com.alumni.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alumni.entity.Message;
import com.alumni.entity.User;
import com.alumni.repository.MessageRepository;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserService userService;

    public Message sendMessage(Long receiverId, String content, String senderEmail) {

        try {
            System.out.println("========== SEND MESSAGE ==========");
            System.out.println("Sender Email: " + senderEmail);
            System.out.println("Receiver Id: " + receiverId);
            System.out.println("Content: " + content);

            User sender = userService.getCurrentUser(senderEmail);
            System.out.println("Sender Found: " + sender.getId());

            User receiver = userService.getUserById(receiverId);
            System.out.println("Receiver Found: " + receiver.getId());

            Message message = new Message();
            message.setSender(sender);
            message.setReceiver(receiver);
            message.setContent(content);

            System.out.println("Saving message...");

            Message savedMessage = messageRepository.save(message);

            System.out.println("Message Saved Successfully!");
            System.out.println("Message ID: " + savedMessage.getId());

            return savedMessage;

        } catch (Exception e) {
            System.out.println("ERROR WHILE SENDING MESSAGE");
            e.printStackTrace();
            throw e;
        }
    }

    public List<Message> getConversation(Long otherUserId, String currentUserEmail) {

        User currentUser = userService.getCurrentUser(currentUserEmail);
        User otherUser = userService.getUserById(otherUserId);

        List<Message> messages =
                messageRepository.findConversation(currentUser, otherUser);

        messages.stream()
                .filter(m ->
                        m.getReceiver().getId().equals(currentUser.getId())
                                && !m.isRead())
                .forEach(m -> {
                    m.setRead(true);
                    messageRepository.save(m);
                });

        return messages;
    }

    public List<User> getConversationPartners(String email) {
        User user = userService.getCurrentUser(email);
        return messageRepository.findConversationPartners(user);
    }

    public long getUnreadCount(String email) {
        User user = userService.getCurrentUser(email);
        return messageRepository.countByReceiverAndReadFalse(user);
    }
}