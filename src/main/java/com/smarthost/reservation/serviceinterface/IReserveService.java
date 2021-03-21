package com.smarthost.reservation.serviceinterface;

import java.util.List;

import com.smarthost.reservation.dto.RoomUsageDetailsDTO;

public interface IReserveService {
	public void initGuestPriceQuotes(List<Integer> guestPriceQuotes);

	public RoomUsageDetailsDTO getRoomUsageDetails(int inputPremiumRoomCount, int inputEconomyRoomCount);

}
