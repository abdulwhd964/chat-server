/**
 * Author: Abdul Wahid
 * Date:18/05/2024
 * Time:8:59 PM
 */
package com.chatserver.mapper;

import com.chatserver.dto.ChatMessageDTO;
import com.chatserver.entity.ChatMessage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ChatMapper {

    @Mapping(source = "id", target = "id")
    @Mapping(source = "sender", target = "sender")
    @Mapping(source = "content", target = "content")
    @Mapping(source = "timestamp", target = "timestamp")
    @Mapping(source = "currentUserName", target = "currentUserName")
    @Mapping(source = "type", target = "type")
    ChatMessageDTO entityToDTO(ChatMessage entity);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "sender", target = "sender")
    @Mapping(source = "content", target = "content")
    @Mapping(source = "timestamp", target = "timestamp")
    @Mapping(source = "currentUserName", target = "currentUserName")
    @Mapping(source = "type", target = "type")
    ChatMessage DtoToEntity(ChatMessageDTO chatMessageDTO);

    List<ChatMessageDTO> entityToList(List<ChatMessage> chatMessage);
}
