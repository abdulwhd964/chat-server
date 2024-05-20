/**
 * Author: Abdul Wahid
 * Date:18/05/2024
 * Time:8:52 AM
 */
package com.chatserver.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChatMessage {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	String sender;
	String currentUserName;

	String type;

	String content;
	LocalDateTime timestamp;

	@PrePersist
	public void prePersist() {
		timestamp = LocalDateTime.now();
	}
}
