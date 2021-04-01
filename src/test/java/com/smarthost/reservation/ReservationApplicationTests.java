package com.smarthost.reservation;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.smarthost.reservation.controller.ReserveController;

//smoke test case
@SpringBootTest
class ReservationApplicationTests {

	@Autowired
	ReserveController reserveController;

	@Test
	void contextLoads() {
		assertThat(reserveController).isNotNull();
	}

}
