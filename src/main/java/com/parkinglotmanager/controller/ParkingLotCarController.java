package com.parkinglotmanager.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.parkinglotmanager.ParkingLotEntryRS;
import com.parkinglotmanager.ParkingLotExitRS;

@RestController
@RequestMapping("parkinglotmanager")
public class ParkingLotCarController {

	@PostMapping(path = "/parkinglot/{parkingLotCode}/carParked", consumes = "application/json", produces = "application/json")
	public ParkingLotEntryRS createParkingLotEntry(@PathVariable String parkingLotCode,
			@RequestBody ParkingLotEntryRS parkingLotCreateBody) {
		return new ParkingLotEntryRS();
	}

	@DeleteMapping(path = "/parkinglot/{parkingLotCode}/carParked", consumes = "application/json", produces = "application/json")
	public ParkingLotExitRS deleteParkingLotEntry(@PathVariable String parkingLotCode) {
		return new ParkingLotExitRS();
	}
}
