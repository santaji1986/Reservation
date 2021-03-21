package com.smarthost.reservation.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(ReserveController.class)
class ReserveControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	@DisplayName("Test hello endpoint without query param")
	void helloWithoutQueryParam() throws Exception {
		// @formatter:off
		this.mockMvc.perform(
				get("/hello")
			)
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(content().string("Hello "));
	// @formatter:on
	}

	@Test
	@DisplayName("Test hello endpoint with query param")
	void helloWithQueryParam() throws Exception {
		// @formatter:off
		this.mockMvc.perform(
				get("/hello")
				.queryParam("name", "san")
			)
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(content().string("Hello san"));
	// @formatter:on
	}

}
