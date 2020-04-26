package com.parkinglotmanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.parkinglotmanager.ParkingLotEntryRQ;
import com.parkinglotmanager.ParkingLotEntryRS;
import com.parkinglotmanager.ParkingLotExitRS;
import com.parkinglotmanager.mapper.ParkingLotCarMapper;
import com.parkinglotmanager.service.CarBom;
import com.parkinglotmanager.service.ParkingLotCarServiceImpl;

@RestController
@RequestMapping("parkinglotmanager")
public class ParkingLotCarController {

	@Autowired
	private ParkingLotCarMapper mapper;

	@Autowired
	private ParkingLotCarServiceImpl service;

	@PostMapping(path = "/parkinglot/{parkingLotCode}/carParked", consumes = "application/json", produces = "application/json")
	public ParkingLotEntryRS createParkingLotEntry(@PathVariable String parkingLotCode,
			@RequestBody ParkingLotEntryRQ parkingLotEntry) {
		CarBom response = service.create(mapper.mapToCarBom(parkingLotCode, parkingLotEntry));
		return mapper.mapToParkingLotEntryRS(response);
	}

	@DeleteMapping(path = "/parkinglot/{parkingLotCode}/carParked/{carID}", consumes = "application/json", produces = "application/json")
	public ParkingLotExitRS deleteParkingLotEntry(@PathVariable String parkingLotCode, @PathVariable String carID) {
		CarBom response = service.delete(mapper.mapToCarBom(parkingLotCode, carID));
		return mapper.mapToParkingLotExitRS(response);
	}
}
