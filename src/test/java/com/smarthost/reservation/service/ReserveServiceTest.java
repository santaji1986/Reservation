package com.smarthost.reservation.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import com.smarthost.reservation.dto.RoomUsageDetailsDTO;

@SpringBootTest
@ContextConfiguration
public class ReserveServiceTest {
	@Resource
	ReserveService reserveService;

	@BeforeEach
	public void beforeEach(TestInfo info) {
		List<Integer> guestPriceQuote = Arrays.asList(23, 45, 155, 374, 22, 99, 100, 101, 115, 209);
		reserveService.initGuestPriceQuotes(guestPriceQuote);
	}

	@Test
	@DisplayName("Test when no guest price quote is available, no or null room usage is expected.")
	void inputRoomsWhenEmptyGuestPriceQuotes() {
		// prepare
		int inputPremiumRoomCount = 2;
		int inputEconomyRoomCount = 2;

		int premiumRoomCount = 0;
		int premiumRoomUsage = 0;
		int economyRoomCount = 0;
		int economyRoomUsage = 0;

		reserveService.initGuestPriceQuotes(Collections.emptyList());

		RoomUsageDetailsDTO roomUsageDetailsDTOExpected;
		// @formatter:off
		roomUsageDetailsDTOExpected = RoomUsageDetailsDTO.builder()
										.premiumRoomCount(premiumRoomCount)
										.premiumRoomUsage(premiumRoomUsage)
										.economyRoomCount(economyRoomCount)
										.economyRoomUsage(economyRoomUsage)
										.build();
		// @formatter:on

		// execute
		RoomUsageDetailsDTO roomUsageDetailsDTOActual = reserveService.getRoomUsageDetails(inputPremiumRoomCount,
				inputEconomyRoomCount);
		// verify
		assertEquals(roomUsageDetailsDTOExpected, roomUsageDetailsDTOActual);
	}

	@ParameterizedTest
	// @formatter:off
 	@CsvSource({ 
 		"0, 0, 0, 0, 0, 0",
 		"0, 1, 0, 1, 0, 99",
 		"1, 0, 1, 0, 374, 0",
 		"5, 5, 5, 4, 954, 189",
 		"3, 3, 3, 3, 738, 167" ,
		"7, 5, 6, 4, 1054, 189", 
		"2, 7, 2, 4, 583, 189", 
		"7, 1, 7, 1, 1153, 45",
		"1, 1, 1, 1, 374, 99"
		})
	// @formatter:on
	void inputRoomsTest(int inputPremiumRoomCount, int inputEconomyRoomCount, int premiumRoomCount,
			int economyRoomCount, int premiumRoomUsage, int economyRoomUsage) {
		// prepare
		RoomUsageDetailsDTO roomUsageDetailsDTOExpected;
		// @formatter:off
		roomUsageDetailsDTOExpected = RoomUsageDetailsDTO.builder()
										.premiumRoomCount(premiumRoomCount)
										.premiumRoomUsage(premiumRoomUsage)
										.economyRoomCount(economyRoomCount)
										.economyRoomUsage(economyRoomUsage)
										.build();
		// @formatter:on
		// execute
		RoomUsageDetailsDTO roomUsageDetailsDTOActual = reserveService.getRoomUsageDetails(inputPremiumRoomCount,
				inputEconomyRoomCount);
		// verify
		assertEquals(roomUsageDetailsDTOExpected, roomUsageDetailsDTOActual);
	}

	@Test
	void inputRoomsTest_whenAllBudgetIsNotPremium() {
		// prepare
		int inputPremiumRoomCount = 2;
		int inputEconomyRoomCount = 2;

		int premiumRoomCount = 2;
		int premiumRoomUsage = 120;
		int economyRoomCount = 2;
		int economyRoomUsage = 40;

		reserveService.initGuestPriceQuotes(Arrays.asList(25, 15, 35, 85));

		RoomUsageDetailsDTO roomUsageDetailsDTOExpected;
		// @formatter:off
		roomUsageDetailsDTOExpected = RoomUsageDetailsDTO.builder()
										.premiumRoomCount(premiumRoomCount)
										.premiumRoomUsage(premiumRoomUsage)
										.economyRoomCount(economyRoomCount)
										.economyRoomUsage(economyRoomUsage)
										.build();
		// @formatter:on

		// execute
		RoomUsageDetailsDTO roomUsageDetailsDTOActual = reserveService.getRoomUsageDetails(inputPremiumRoomCount,
				inputEconomyRoomCount);
		// verify
		assertEquals(roomUsageDetailsDTOExpected, roomUsageDetailsDTOActual);

	}
}
