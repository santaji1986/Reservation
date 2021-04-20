package com.smarthost.reservation.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.smarthost.reservation.dto.RoomUsageDetailsDTO;
import com.smarthost.reservation.serviceinterface.IReserveService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController

@Api(value = "Reservation")
public class ReserveController {
	private IReserveService reserveService;

	public ReserveController(IReserveService reserveService) {
		this.reserveService = reserveService;
	}

	@GetMapping("/hello")
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(httpMethod = "GET", notes = "Hello test request", value = "Hello requestparameter")
	String hello(@RequestParam(required = false, defaultValue = "") String name) {
		return "Hello " + name;
	}

	@GetMapping("/initGuestPriceQuotes")
	ResponseEntity<?> initGuestPriceQuotes(@RequestParam List<Float> guestPriceQuotes) {
		reserveService.initGuestPriceQuotes(guestPriceQuotes);
		return ResponseEntity.ok().build();
	}

	@GetMapping("/getRoomUsageDetails")
	RoomUsageDetailsDTO getRoomUsageDetails(@RequestParam int inputPremiumRoomCount,
			@RequestParam int inputEconomyRoomCount) {
		RoomUsageDetailsDTO usageDetailsDTO = reserveService.getRoomUsageDetails(inputPremiumRoomCount,
				inputEconomyRoomCount);
		return usageDetailsDTO;
	}

}
