/**
 * Author: Abdul Wahid
 * Date:18/05/2024
 * Time:10:29 PM
 */
package com.chatserver.exception;

public class MessageNotFoundException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5538840086554832350L;

	public MessageNotFoundException(String message) {
		super(message);
	}
}
