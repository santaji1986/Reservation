package com.smarthost.reservation.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Collections;

import javax.annotation.Resource;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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
	@CsvSource({ "0, 0, 0, 0, 0, 0" })
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
}
