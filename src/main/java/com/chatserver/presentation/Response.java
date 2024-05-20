/**
 * Author: Abdul Wahid
 * Date:18/05/2024
 * Time:9:53 PM
 */
package com.chatserver.presentation;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class Response {

	String message;
	Object data;

	public Response(String message, Object data) {
		this.message = message;
		this.data = data;
	}

	public String getMessage() {
		return message;
	}

	public Object getData() {
		return data;
	}
}
