/**
 * Author: Abdul Wahid
 * Date:18/05/2024
 * Time:9:04 AM
 */
package com.chatserver.websocket;

import java.security.Principal;

import com.chatserver.constant.ChatAppConstant;
import com.chatserver.dto.ChatMessageDTO;
import com.chatserver.dto.DeleteMessageRequestDto;
import com.chatserver.mapper.ChatMapper;
import com.chatserver.presentation.ErrorResponse;
import com.chatserver.presentation.Response;
import com.chatserver.presentation.ResponseBuilder;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;

import com.chatserver.entity.ChatMessage;
import com.chatserver.service.ChatMessageService;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;

@Controller
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MessageHandler {

    ChatMessageService chatMessageService;

    ChatMapper chatMapper;

    @MessageMapping("/chat")
    @SendTo("/topic/messages")
    public Response handleMessage(@AuthenticationPrincipal Principal user, ChatMessageDTO message) {
        message.setSender(user.getName());
        message.setCurrentUserName(user.getName());
        return chatMessageService.saveMessage(message);
    }

    @MessageMapping("/delete")
    @SendTo("/topic/delete/messages")
    public Response deleteMessage(@AuthenticationPrincipal Principal user,@Payload DeleteMessageRequestDto deleteRequest) {
        ChatMessage chatMessage=chatMessageService.getMessageById(deleteRequest.getId());
        if(!chatMessage.getSender().equals(user.getName())){
            return new ResponseBuilder().message(ChatAppConstant.ERROR).data("You are not the sender of this message.").build();
        }
        chatMessageService.deleteMessage(deleteRequest.getId(), user.getName());
        deleteRequest.setType("DELETE");
        return new ResponseBuilder().message(ChatAppConstant.SUCCESS).data(deleteRequest).build();
    }
}
