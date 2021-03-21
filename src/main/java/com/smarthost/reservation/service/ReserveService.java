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

	private List<Integer> guestPriceQuotes = new ArrayList();

	public void initGuestPriceQuotes(List<Integer> guestPriceQuote) {
		this.guestPriceQuotes = guestPriceQuote;
		guestPriceQuotes.sort(Comparator.reverseOrder());
	}

	public RoomUsageDetailsDTO getRoomUsageDetails(int inputPremiumRoomCount, int inputEconomyRoomCount) {
		int currentCount = 0;
		int premiumRoomUsed = 0;
		int economyRoomUsed = 0;
		RoomUsageDetailsDTO roomUsageDetailsDTO = new RoomUsageDetailsDTO();
		while (currentCount < guestPriceQuotes.size() && isPremiumRoomAssignable(inputPremiumRoomCount, premiumRoomUsed,
				guestPriceQuotes.get(currentCount))) {
			roomUsageDetailsDTO = reservePremiumRoom(premiumRoomUsed++, roomUsageDetailsDTO,
					guestPriceQuotes.get(currentCount++));
		}
		while (isUpgradeToPremiumApplicable(premiumRoomUsed, inputPremiumRoomCount, inputEconomyRoomCount)) {
			roomUsageDetailsDTO = reservePremiumRoom(premiumRoomUsed++, roomUsageDetailsDTO,
					guestPriceQuotes.get(currentCount++));
		}
		while (economyRoomUsed < inputEconomyRoomCount && currentCount < guestPriceQuotes.size()) {
			if (isEconomyRoomAssignable(inputEconomyRoomCount, economyRoomUsed, guestPriceQuotes.get(currentCount))) {
				roomUsageDetailsDTO = reserveEconomyRoom(economyRoomUsed++, roomUsageDetailsDTO,
						guestPriceQuotes.get(currentCount++));
			} else
				currentCount++;
		}
		return roomUsageDetailsDTO;
	}

	private RoomUsageDetailsDTO reserveEconomyRoom(int i, RoomUsageDetailsDTO roomUsageDetailsDTO,
			Integer currentGuestPrice) {
		roomUsageDetailsDTO.setEconomyRoomUsage(roomUsageDetailsDTO.getEconomyRoomUsage() + currentGuestPrice);
		roomUsageDetailsDTO.setEconomyRoomCount(roomUsageDetailsDTO.getEconomyRoomCount() + 1);
		return roomUsageDetailsDTO;
	}

	private boolean isEconomyRoomAssignable(int inputEconomyRoomCount, int economyRoomUsed, Integer currentGuestPrice) {
		return currentGuestPrice < thresholdGuestPriceQuote && economyRoomUsed < inputEconomyRoomCount;
	}

	private boolean isUpgradeToPremiumApplicable(int premiumRoomUsed, int inputPremiumRoomCount,
			int inputEconomyRoomCount) {
		return premiumRoomUsed < inputPremiumRoomCount
				&& (guestPriceQuotes.size() - (inputEconomyRoomCount + premiumRoomUsed)) > 0;
	}

	private RoomUsageDetailsDTO reservePremiumRoom(int premiumRoomUsed, RoomUsageDetailsDTO roomUsageDetailsDTO,
			Integer currentGuestPrice) {
		roomUsageDetailsDTO.setPremiumRoomUsage(roomUsageDetailsDTO.getPremiumRoomUsage() + currentGuestPrice);
		roomUsageDetailsDTO.setPremiumRoomCount(roomUsageDetailsDTO.getPremiumRoomCount() + 1);
		return roomUsageDetailsDTO;
	}

	private boolean isPremiumRoomAssignable(int inputPremiumRoomCount, int premiumRoomUsed, Integer currentGuestPrice) {
		return currentGuestPrice >= thresholdGuestPriceQuote && premiumRoomUsed < inputPremiumRoomCount;
	}
}
