package com.github.ships.ships.websocket;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class WebsocketService {

    private SimpMessagingTemplate messagingTemplate;

    public WebsocketService(final SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public void notifyFrontEnd(String username, Event event) {
        messagingTemplate.convertAndSendToUser(username, "/events", event);
    }
}
