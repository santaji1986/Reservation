package com.smarthost.reservation.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.smarthost.reservation.dto.RoomUsageDetailsDTO;
import com.smarthost.reservation.serviceinterface.IReserveService;

@Service
public class ReserveService implements IReserveService {
	@Value("${thresholdGuestPriceQuote}")
	private Float thresholdGuestPriceQuote;

	private List<Float> guestPriceQuotes = new ArrayList<Float>();

	public void initGuestPriceQuotes(List<Float> guestPriceQuote) {
		this.guestPriceQuotes = guestPriceQuote;
		guestPriceQuotes.sort(Comparator.reverseOrder());
	}

	public RoomUsageDetailsDTO getRoomUsageDetails(int inputPremiumRoomCount, int inputEconomyRoomCount) {
		int currentCount = 0;
		int premiumRoomUsed = 0;
		int economyRoomUsed = 0;
		RoomUsageDetailsDTO roomUsageDetailsDTO = new RoomUsageDetailsDTO(0, 0f, 0, 0f);
		while (currentCount < guestPriceQuotes.size() && isPremiumRoomAssignable(inputPremiumRoomCount, premiumRoomUsed,
				guestPriceQuotes.get(currentCount))) {
			roomUsageDetailsDTO = reservePremiumRoom(roomUsageDetailsDTO, guestPriceQuotes.get(currentCount));
			premiumRoomUsed++;
			currentCount++;
		}
		while (isUpgradeToPremiumApplicable(premiumRoomUsed, inputPremiumRoomCount, inputEconomyRoomCount)) {
			roomUsageDetailsDTO = reservePremiumRoom(roomUsageDetailsDTO, guestPriceQuotes.get(currentCount));
			premiumRoomUsed++;
			currentCount++;
		}
		while (economyRoomUsed < inputEconomyRoomCount && currentCount < guestPriceQuotes.size()) {
			if (isEconomyRoomAssignable(inputEconomyRoomCount, economyRoomUsed, guestPriceQuotes.get(currentCount))) {
				roomUsageDetailsDTO = reserveEconomyRoom(roomUsageDetailsDTO, guestPriceQuotes.get(currentCount));
				economyRoomUsed++;
				currentCount++;
			} else
				currentCount++;
		}
		return roomUsageDetailsDTO;
	}

	private RoomUsageDetailsDTO reserveEconomyRoom(RoomUsageDetailsDTO roomUsageDetailsDTO, Float currentGuestPrice) {
		RoomUsageDetailsDTO roomUsageDetailsDTONew = RoomUsageDetailsDTO.builder()
				.premiumRoomCount(roomUsageDetailsDTO.getPremiumRoomCount())
				.premiumRoomUsage(roomUsageDetailsDTO.getPremiumRoomUsage())
				.economyRoomCount(roomUsageDetailsDTO.getEconomyRoomCount() + 1)
				.economyRoomUsage(roomUsageDetailsDTO.getEconomyRoomUsage() + currentGuestPrice)
				.build();

		return roomUsageDetailsDTONew;
	}

	private boolean isEconomyRoomAssignable(int inputEconomyRoomCount, int economyRoomUsed, Float currentGuestPrice) {
		return currentGuestPrice < thresholdGuestPriceQuote && economyRoomUsed < inputEconomyRoomCount;
	}

	private boolean isUpgradeToPremiumApplicable(int premiumRoomUsed, int inputPremiumRoomCount,
			int inputEconomyRoomCount) {
		return premiumRoomUsed < inputPremiumRoomCount
				&& (guestPriceQuotes.size() - (inputEconomyRoomCount + premiumRoomUsed)) > 0;
	}

	private RoomUsageDetailsDTO reservePremiumRoom(RoomUsageDetailsDTO roomUsageDetailsDTO, Float currentGuestPrice) {
		RoomUsageDetailsDTO roomUsageDetailsDTONew = RoomUsageDetailsDTO.builder()
				.premiumRoomCount(roomUsageDetailsDTO.getPremiumRoomCount() + 1)
				.premiumRoomUsage(roomUsageDetailsDTO.getPremiumRoomUsage() + currentGuestPrice)
				.economyRoomCount(roomUsageDetailsDTO.getEconomyRoomCount())
				.economyRoomUsage(roomUsageDetailsDTO.getEconomyRoomUsage())
				.build();
		return roomUsageDetailsDTONew;
	}

	private boolean isPremiumRoomAssignable(int inputPremiumRoomCount, int premiumRoomUsed, Float currentGuestPrice) {
		return currentGuestPrice >= thresholdGuestPriceQuote && premiumRoomUsed < inputPremiumRoomCount;
	}
}
