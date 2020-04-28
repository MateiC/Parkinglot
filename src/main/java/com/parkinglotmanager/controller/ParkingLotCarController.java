package com.parkinglotmanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.parkinglotmanager.CarType;
import com.parkinglotmanager.ParkingLotEntry;
import com.parkinglotmanager.ParkingLotExit;
import com.parkinglotmanager.mapper.ParkingLotCarMapper;
import com.parkinglotmanager.service.CarBom;
import com.parkinglotmanager.service.interfaces.IParkingLotCarService;

@RestController
@RequestMapping("parkinglotmanager")
public class ParkingLotCarController {

	@Autowired
	private ParkingLotCarMapper mapper;

	@Autowired
	private IParkingLotCarService service;

	@PostMapping(path = "/parkinglot/{parkingLotCode}/carParked/{carID}", consumes = "application/json", produces = "application/json")
	public ParkingLotEntry createParkingLotEntry(@PathVariable String parkingLotCode, @PathVariable String carID,
			@RequestParam("carType") CarType carType) {
		CarBom response = service.create(mapper.mapToCarBomCreate(parkingLotCode, carID, carType));
		return mapper.mapToParkingLotEntry(response);
	}

	@DeleteMapping(path = "/parkinglot/{parkingLotCode}/carParked/{carID}", consumes = "application/json", produces = "application/json")
	public ParkingLotExit deleteParkingLotEntry(@PathVariable String parkingLotCode, @PathVariable String carID) {
		CarBom response = service.delete(mapper.mapToCarBomDelete(parkingLotCode, carID));
		return mapper.mapToParkingLotExit(response);
	}
}
