/**
 * Author: Abdul Wahid
 * Date:18/05/2024
 * Time:8:55 AM
 */
package com.chatserver.controller;

import com.chatserver.constant.ChatAppConstant;
import com.chatserver.dto.ChatMessageDTO;
import com.chatserver.entity.ChatMessage;
import com.chatserver.presentation.Response;
import com.chatserver.presentation.ResponseBuilder;
import com.chatserver.service.ChatMessageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/chat")
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ChatController {
    ChatMessageService chatMessageService;

    @Tag(name = "post an message")
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Created. The message was successfully posted."), @ApiResponse(responseCode = "400", description = "Returned when there are validation errors in the submitted form data."), @ApiResponse(responseCode = "401", description = "Unauthorized - Invalid authorization"), @ApiResponse(responseCode = "500", description = "Returned when an unexpected internal server error occurs.")})
    @Operation(summary = "post an message")
    @PostMapping("/message")
    public ResponseEntity<Response> sendMessage(@Valid @RequestBody ChatMessageDTO message) {
        message.setSender(SecurityContextHolder.getContext().getAuthentication().getName());
        message.setCurrentUserName(message.getSender());
        return new ResponseEntity<>(chatMessageService.saveMessage(message), HttpStatus.CREATED);
    }

    @Tag(name = "Get message by sender")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK. The operation was successful."), @ApiResponse(responseCode = "401", description = "Unauthorized - Invalid authorization"), @ApiResponse(responseCode = "404", description = "Returned when the provided sender is not found."), @ApiResponse(responseCode = "500", description = "Returned when an unexpected internal server error occurs.")})
    @Operation(summary = "get message by sender")
    @GetMapping("/messages/sender")
    public Response getMessagesBySender() {
        return chatMessageService.getMessagesBySender(SecurityContextHolder.getContext().getAuthentication().getName());
    }

    @Tag(name = "Get All messages")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK. The operation was successful."), @ApiResponse(responseCode = "401", description = "Unauthorized - Invalid authorization"), @ApiResponse(responseCode = "500", description = "Returned when an unexpected internal server error occurs.")})
    @Operation(summary = "get all messages")
    @GetMapping("/messages")
    public Response getMessages() {
        return chatMessageService.getMessages();
    }

    @Tag(name = "Delete message by id")
    @Operation(summary = "delete message", description = "Message must exist")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "No content. The message was successfully deleted."), @ApiResponse(responseCode = "400", description = "If non sender trying to delete"), @ApiResponse(responseCode = "404", description = "Returned when the provided message id is not found."), @ApiResponse(responseCode = "404", description = "Returned when the provided sender is not found."), @ApiResponse(responseCode = "401", description = "Unauthorized - Invalid authorization"), @ApiResponse(responseCode = "500", description = "Returned when an unexpected internal server error occurs.")})
    @DeleteMapping("/message/{id}")
    public ResponseEntity<Response> deleteMessage(@PathVariable Long id, @RequestParam String username) {
        ChatMessage chatMessage = chatMessageService.getMessageById(id);
        if (!chatMessage.getSender().equals(SecurityContextHolder.getContext().getAuthentication().getName())) {
            return new ResponseEntity<>(new ResponseBuilder().message(ChatAppConstant.ERROR).data("You are not the sender of this message.").build(), HttpStatus.BAD_REQUEST);
        }
        chatMessageService.deleteMessage(id, username);
        return null;
    }
}

