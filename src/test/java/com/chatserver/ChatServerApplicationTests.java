package com.chatserver;

import com.chatserver.entity.ChatMessage;
import com.chatserver.presentation.Response;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.Test;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FieldDefaults(level = AccessLevel.PRIVATE)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@WithMockUser(username = "abdul",password = "abdul")
public class ChatServerApplicationTests {

	private static final String appUrl="/api/chat";

	@Autowired
	MockMvc mvc;

	@Autowired
	ObjectMapper objectMapper;

	@Test
	public void testGetAllMessage() throws Exception {
		mvc.perform(get(appUrl + "/messages")
						.with(httpBasic("abdul", "abdul"))
						.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk());
	}

	@Test
	public void testGetAllMessageBySender() throws Exception {
		mvc.perform(get(appUrl + "/messages/sender")
						.with(httpBasic("abdul", "abdul"))
						.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk());
	}

	@Test
	@Order(0)
	public void testPostInsertMessage() throws Exception {
		ChatMessage mockChatMessage = new ChatMessage();
		mockChatMessage.setContent("Hello");
		mockChatMessage.setType("chat");
		// Performing the POST request

		ResultActions resultActions = mvc
				.perform(MockMvcRequestBuilders.post(appUrl+"/message").contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(mockChatMessage)))
				.andExpect(MockMvcResultMatchers.status().isCreated());

		ChatMessage chatMessageResponse = convertResultActionsToChatMessage(resultActions);

		assertAll(() -> assertNotNull(chatMessageResponse),
				() -> assertEquals("abdul", chatMessageResponse.getSender()));
	}

	@Test
	public void testPostDeletedMessage() throws Exception {
		// Performing the DELETE request
		mvc.perform(MockMvcRequestBuilders.delete(appUrl+"/message/1?username=abdul"))
				.andExpect(MockMvcResultMatchers.status().isNoContent());
	}

	@Test
	public void testShouldNotPostAnMessage() throws Exception {
		ChatMessage mockChatMessage = new ChatMessage();
		mockChatMessage.setContent(null);
		// Performing the POST request
		ResultActions resultActions = mvc
				.perform(MockMvcRequestBuilders.post(appUrl+"/message").contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(mockChatMessage)))
				.andExpect(MockMvcResultMatchers.status().isBadRequest());

		String responseBody = resultActions.andReturn().getResponse().getContentAsString();
		assertAll(() -> assertEquals("content is required", JsonPath.read(responseBody, "$.message")));
	}

	private ChatMessage convertResultActionsToChatMessage(final ResultActions resultActions)
			throws UnsupportedEncodingException, JsonProcessingException {
		return objectMapper.readValue(objectMapper.writeValueAsString(objectMapper
						.readValue(resultActions.andReturn().getResponse().getContentAsString(), Response.class).getData()),
				ChatMessage.class);
	}
}
