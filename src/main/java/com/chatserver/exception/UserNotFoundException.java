/**
 * Author: Abdul Wahid
 * Date:18/05/2024
 * Time:1:38 PM
 */
package com.chatserver.exception;

import org.springframework.security.core.AuthenticationException;

public class UserNotFoundException extends AuthenticationException {
    /**
	 * 
	 */
	private static final long serialVersionUID = -6242031645986494898L;

	public UserNotFoundException(String message) {
        super(message);
    }
}
