package com.exmple.dinuk.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

@Service
@CrossOrigin
public class NotificationImpl {
    //send messages to WebSocket clients.
    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    // This line assigns the messagingTemplate parameter to the messagingTemplate field of the NotificationImpl class.
    public NotificationImpl(final SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public void notifyNewPost(final String message) {
        messagingTemplate.convertAndSend("/topic/new-post", message);
    }
}
