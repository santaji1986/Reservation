package com.smarthost.reservation.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.smarthost.reservation.dto.RoomUsageDetailsDTO;
@Service
public class ReserveService {

	public void initGuestPriceQuotes(List<Object> emptyList) {

	}

	public RoomUsageDetailsDTO getRoomUsageDetails(int inputPremiumRoomCount, int inputEconomyRoomCount) {
		RoomUsageDetailsDTO roomUsageDetailsDTO = new RoomUsageDetailsDTO();
		return roomUsageDetailsDTO;
	}

}
