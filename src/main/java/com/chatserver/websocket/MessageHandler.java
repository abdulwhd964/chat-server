/**
 * Author: Abdul Wahid
 * Date:18/05/2024
 * Time:9:04 AM
 */
package com.chatserver.websocket;

import com.chatserver.constant.ChatAppConstant;
import com.chatserver.dto.ChatMessageDTO;
import com.chatserver.dto.DeleteMessageRequestDto;
import com.chatserver.entity.ChatMessage;
import com.chatserver.mapper.ChatMapper;
import com.chatserver.presentation.Response;
import com.chatserver.presentation.ResponseBuilder;
import com.chatserver.service.ChatMessageService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MessageHandler {

	ChatMessageService chatMessageService;

	ChatMapper chatMapper;

	/**
	 * post an message and send it to an topic
	 * 
	 * @param user
	 * @param message
	 * @return
	 */
	@MessageMapping("/chat")
	@SendTo("/topic/messages")
	public ResponseEntity<Response> handleMessage(@AuthenticationPrincipal Principal user, ChatMessageDTO message) {
		message.setSender(user.getName());
		message.setCurrentUserName(user.getName());
		return new ResponseEntity<>(chatMessageService.saveMessage(message), HttpStatus.CREATED);
	}

	/**
	 * delete message and send it to topic
	 * 
	 * @param user
	 * @param deleteRequest
	 * @return
	 */
	@MessageMapping("/delete")
	@SendTo("/topic/delete/messages")
	public ResponseEntity<Response> deleteMessage(@AuthenticationPrincipal Principal user,
			@Payload DeleteMessageRequestDto deleteRequest) {
		ChatMessage chatMessage = chatMessageService.getMessageById(deleteRequest.getId());
		if (!chatMessage.getSender().equals(user.getName())) {
			return new ResponseEntity<>(new ResponseBuilder().message(ChatAppConstant.ERROR)
					.data("You are not the sender of this message.").build(), HttpStatus.BAD_REQUEST);
		}
		chatMessageService.deleteMessage(deleteRequest.getId(), user.getName());
		deleteRequest.setType("DELETE");
		return new ResponseEntity<>(new ResponseBuilder().message(ChatAppConstant.SUCCESS).data(deleteRequest).build(),
				HttpStatus.NO_CONTENT);
	}
}
