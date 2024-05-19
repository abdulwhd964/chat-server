/**
 * Author: Abdul Wahid
 * Date:18/05/2024
 * Time:8:56 AM
 */
package com.chatserver.service;

import com.chatserver.constant.ChatAppConstant;
import com.chatserver.dto.ChatMessageDTO;
import com.chatserver.entity.ChatMessage;
import com.chatserver.entity.User;
import com.chatserver.exception.MessageNotFoundException;
import com.chatserver.exception.UserNotFoundException;
import com.chatserver.mapper.ChatMapper;
import com.chatserver.presentation.Response;
import com.chatserver.presentation.ResponseBuilder;
import com.chatserver.repository.ChatMessageRepository;
import com.chatserver.repository.UserRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ChatMessageService {

    ChatMessageRepository chatMessageRepository;

    UserRepository userRepository;

    ChatMapper chatMapper;

    @Transactional
    @Caching(evict = { @CacheEvict(value = "chat", allEntries = true) })
    public Response saveMessage(final ChatMessageDTO message) {
        return new ResponseBuilder().message(ChatAppConstant.SUCCESS)
                .data(chatMapper.entityToDTO(chatMessageRepository.save(chatMapper.DtoToEntity(message)))).build();
    }

    @Cacheable(value = "chat",key = "#sender")
    public Response getMessagesBySender(final String sender) {
        return new ResponseBuilder().message(ChatAppConstant.SUCCESS)
                .data(chatMapper.entityToList(chatMessageRepository.findAllBySenderOrderByTimestampAsc(findBySender(sender)))).build();
    }

    @Cacheable(value = "chat",key = "#sender")
    private String findBySender(final String sender){
        User user = userRepository.findByUsername(sender).orElseThrow(() -> new UserNotFoundException(String.format("Sender: %s not found", sender)));
        return user.getUsername();
    }

    private ChatMessage findById(final Long id){
       return chatMessageRepository.findById(id).orElseThrow(() -> new MessageNotFoundException(String.format("Message id : %s not found", id)));
    }

    @Cacheable("chat")
    public Response getMessages() {
        return new ResponseBuilder().message(ChatAppConstant.SUCCESS)
                .data(chatMapper.entityToList(chatMessageRepository.findAllByOrderByTimestampAsc())).build();
    }

    @CacheEvict(value = "chat", key = "#id", allEntries = true)
    public void deleteMessage(final Long id, final String username) {
        findBySender(username);
        ChatMessage message = findById(id);
        if (message != null && message.getSender().equals(username)) {
            chatMessageRepository.delete(message);
        }
    }

    public ChatMessage getMessageById(long id) {
        return  chatMessageRepository.findById(id).orElseThrow(() -> new MessageNotFoundException(String.format("Message id : %s not found", id)));
    }
}