package com.chatserver.repository;

import com.chatserver.entity.ChatMessage;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ChatMessageRepository extends CrudRepository<ChatMessage,Long> {
    List<ChatMessage> findAllByOrderByTimestampAsc();

    List<ChatMessage> findAllBySenderOrderByTimestampAsc(String sender);
}
