/**
 * Author: Abdul Wahid
 * Date:18/05/2024
 * Time:9:00 AM
 */
package com.chatserver.websocket;

import com.chatserver.dto.ChatMessageDTO;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class WebSocketEventListener {

	private SimpMessageSendingOperations messagingTemplate;

	@EventListener
	public void handleWebSocketConnectListener(SessionConnectEvent event) {
		log.info("New web socket Connection Established");
	}

	@EventListener
	public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
		SimpMessageHeaderAccessor headerAccessor = SimpMessageHeaderAccessor.wrap(event.getMessage());
		String username = (String) headerAccessor.getSessionAttributes().get("username");
		if (username != null) {
			ChatMessageDTO chatMessageDTO = new ChatMessageDTO();
			chatMessageDTO.setSender(username);
			chatMessageDTO.setCurrentUserName(username);
			chatMessageDTO.setContent("Disconnected");
			messagingTemplate.convertAndSend("/topic/public", chatMessageDTO);
		}
	}
}