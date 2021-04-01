package com.smarthost.reservation.controller;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.smarthost.reservation.dto.RoomUsageDetailsDTO;
import com.smarthost.reservation.serviceinterface.IReserveService;

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

	@MockBean
	private IReserveService reserveService;

	@Test
	@DisplayName("Validate initGuestPriceQuotesTest endpoint for HTTP response code 200")
	public void initGuestPriceQuotesTest() throws Exception {
		List<Float> guestPriceQuotes = Collections.emptyList();
		doNothing().when(reserveService).initGuestPriceQuotes(guestPriceQuotes);
		// @formatter:off
			this.mockMvc.perform(
							get("/initGuestPriceQuotes")
							.queryParam("guestPriceQuotes", "1","2","3")
						)
						.andDo(print())
						.andExpect(status().isOk());
 			// @formatter:on
	}

	@Test
	@DisplayName("Validate JSON response returned from getRoomUsageDetails endpoint")
	public void getRoomUsageDetailsTest() throws Exception {
		RoomUsageDetailsDTO roomUsageDetailsDTO = RoomUsageDetailsDTO.builder()
													.premiumRoomCount(0)
													.premiumRoomUsage(0f)
													.economyRoomCount(0)
													.economyRoomUsage(0f)
													.build();
		when(reserveService.getRoomUsageDetails(Mockito.anyInt(), Mockito.anyInt())).thenReturn(roomUsageDetailsDTO);
		String jsonContent = "{\"premiumRoomCount\":0,\"premiumRoomUsage\":0,\"economyRoomCount\":0,\"economyRoomUsage\":0}";
		// @formatter:off
			this.mockMvc.perform(
							get("/getRoomUsageDetails")
							.queryParam("inputPremiumRoomCount", "1")
							.queryParam("inputEconomyRoomCount", "1")
						)
						.andDo(print())
						.andExpect(status().isOk())
						.andExpect(content().json(jsonContent));
		// @formatter:on

	}

	@Test
	@DisplayName("Test getRoomUsageDetails when inputEconomyRoomCount is supplied with string value instead of integer")
	public void getRoomUsageDetailsTest_BadInputEconomyRoomCountParamValue_BadRequest() throws Exception {
		// @formatter:off
			this.mockMvc.perform(
							get("/getRoomUsageDetails")
							.queryParam("inputPremiumRoomCount", "1")
							.queryParam("inputEconomyRoomCount","a")
						)
						.andDo(print())
						.andExpect(status().isBadRequest());
 		// @formatter:on

	}

	@Test
	@DisplayName("Test getRoomUsageDetails when inputPremiumRoomCount is supplied with string value instead of integer")
	public void getRoomUsageDetailsTest_BadInputPremiumRoomCountValue_BadRequest() throws Exception {
		// @formatter:off
			this.mockMvc.perform(
							get("/getRoomUsageDetails")
							.queryParam("inputPremiumRoomCount", "a")
							.queryParam("inputEconomyRoomCount","1")
						)
						.andDo(print())
						.andExpect(status().isBadRequest());
		// @formatter:on

	}

	@Test
	@DisplayName("Test getRoomUsageDetails when inputPremiumRoomCount is missing")
	public void getRoomUsageDetailsTest_MissingInputPremiumRoomCountValue_BadRequest() throws Exception {
		// @formatter:off
			this.mockMvc.perform(
							get("/getRoomUsageDetails")
							.queryParam("inputEconomyRoomCount","1")
						)
						.andDo(print())
						.andExpect(status().isBadRequest());
 		// @formatter:on

	}

	@Test
	@DisplayName("Test getRoomUsageDetails when inputEconomyRoomCount is missing")
	public void getRoomUsageDetailsTest_MissingInputEconomyRoomCountValue_BadRequest() throws Exception {
		// @formatter:off
			this.mockMvc.perform(
							get("/getRoomUsageDetails")
							.queryParam("inputPremiumRoomCount", "1")
						)
						.andDo(print())
						.andExpect(status().isBadRequest());
		// @formatter:on

	}

}
