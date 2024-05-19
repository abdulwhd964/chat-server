/**
 * Author: Abdul Wahid
 * Date:19/05/2024
 * Time:10:41 AM
 */
package com.chatserver.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DeleteMessageRequestDto {
    long id;

    String type;
}
