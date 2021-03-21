package com.smarthost.reservation.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.smarthost.reservation.dto.RoomUsageDetailsDTO;
import com.smarthost.reservation.serviceinterface.IReserveService;

@RestController
public class ReserveController {
	@Autowired
	private IReserveService reserveService;

	@GetMapping("/hello")
	String hello(@RequestParam(required = false, defaultValue = "") String name) {
		return "Hello " + name;
	}

	@RequestMapping("/initGuestPriceQuotes")
	ResponseEntity<?> initGuestPriceQuotes(@RequestParam List<Integer> guestPriceQuotes) {
		reserveService.initGuestPriceQuotes(guestPriceQuotes);
		return ResponseEntity.ok().build();
	}

	@RequestMapping("/getRoomUsageDetails")
	RoomUsageDetailsDTO getRoomUsageDetails(@RequestParam int inputPremiumRoomCount, @RequestParam int inputEconomyRoomCount) {
		RoomUsageDetailsDTO usageDetailsDTO = reserveService.getRoomUsageDetails(inputPremiumRoomCount, inputEconomyRoomCount);
		return usageDetailsDTO;
	}

}
