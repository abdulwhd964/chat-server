/*
 * Author: Abdul Wahid
 * Date:   23 Feb 2024
 */
package com.chatserver.presentation;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

@ToString
@Data
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ErrorResponse {
	String message;
}
