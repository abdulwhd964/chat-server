/**
 * Author: Abdul Wahid
 * Date:18/05/2024
 * Time:8:58 PM
 */
package com.chatserver.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ChatMessageDTO {

	Long id;

	String sender;

	String type;

	String currentUserName;

	@NotBlank(message = "content is required")
	String content;
	LocalDateTime timestamp;
}
