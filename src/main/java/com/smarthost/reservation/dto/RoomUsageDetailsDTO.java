package com.smarthost.reservation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Value
@AllArgsConstructor
@Builder
public class RoomUsageDetailsDTO {
	private int premiumRoomCount;
	private Float premiumRoomUsage;
	private int economyRoomCount;
	private Float economyRoomUsage;
}
