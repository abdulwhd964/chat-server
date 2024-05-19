/**
 * Author: Abdul Wahid
 * Date:18/05/2024
 * Time:9:54 PM
 */
package com.chatserver.presentation;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class ResponseBuilder {
    String message;
    Object data;

    public ResponseBuilder message(String message) {
        this.message = message;
        return this;
    }

    public ResponseBuilder data(Object data) {
        this.data = data;
        return this;
    }

    public Response build() {
        return new Response(message, data);
    }

}

