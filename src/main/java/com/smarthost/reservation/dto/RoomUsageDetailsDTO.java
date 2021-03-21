package com.smarthost.reservation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoomUsageDetailsDTO {
	private int premiumRoomCount;
	private int premiumRoomUsage;
	private int economyRoomCount;
	private int economyRoomUsage;
}
