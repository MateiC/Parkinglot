package com.parkinglotmanager.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.parkinglotmanager.ParkingLot;
import com.parkinglotmanager.ParkingLotCreate;
import com.parkinglotmanager.ParkingLotEntryRS;
import com.parkinglotmanager.ParkingLotExitRS;
import com.parkinglotmanager.ParkingLotUpdate;
import com.parkinglotmanager.ParkingLotsBasic;
import com.parkinglotmanager.ParkingLotsBasicInner;

@RestController
@RequestMapping("parkinglotmanager")
public class ParkingLotManagerController {

	@GetMapping("/parkinglots")
	public ParkingLotsBasic retrieveAllParkingLots() {
		ParkingLotsBasic response = new ParkingLotsBasic();
		response.add(new ParkingLotsBasicInner().code("test"));
		return response;
	}

	@GetMapping(path = "/parkinglot/{parkingLotCode}", produces = "application/json")
	public ParkingLot retrieveParkingLot(@PathVariable String parkingLotCode) {
		return new ParkingLot().name("test");
	}

	@PostMapping(path = "/parkinglot/{parkingLotCode}", consumes = "application/json", produces = "application/json")
	public ParkingLot createParkingLot(@PathVariable String parkingLotCode,
			@RequestBody ParkingLotCreate parkingLotCreateBody) {
		return new ParkingLot().name("test");
	}

	@PutMapping(path = "/parkinglot/{parkingLotCode}", consumes = "application/json", produces = "application/json")
	public ParkingLot updateParkingLot(@PathVariable String parkingLotCode,
			@RequestBody ParkingLotUpdate parkingLotUpdateBody) {
		return new ParkingLot().name("test");
	}

	@DeleteMapping(path = "/parkinglot/{parkingLotCode}", consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> deleteParkingLot(@PathVariable String parkingLotCode) {
		return ResponseEntity.ok().build();
	}

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
