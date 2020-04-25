package com.parkinglotmanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.parkinglotmanager.ParkingLotUpdate;
import com.parkinglotmanager.ParkingLotsBasic;
import com.parkinglotmanager.mapper.ParkingLotMapper;
import com.parkinglotmanager.repository.models.ParkingLotEntity;
import com.parkinglotmanager.service.ParkingLotServiceImpl;

@RestController
@RequestMapping("parkinglotmanager")
public class ParkingLotController {

	@Autowired
	ParkingLotServiceImpl parkingLotService;

	@Autowired
	ParkingLotMapper parkingLotMapper;

	@GetMapping("/parkinglots")
	public ParkingLotsBasic retrieveAllParkingLots() {
		return parkingLotMapper.mapToRetrieveAll(parkingLotService.retrieveAll());
	}

	@GetMapping(path = "/parkinglot/{parkingLotCode}", produces = "application/json")
	public ParkingLot retrieveParkingLot(@PathVariable String parkingLotCode) {
		return parkingLotMapper.mapToRetrieve(
				parkingLotService.retrieve(parkingLotMapper.mapToParkingLotEntityWithCode(parkingLotCode)));
	}

	@PostMapping(path = "/parkinglot/{parkingLotCode}", consumes = "application/json", produces = "application/json")
	public ParkingLot createParkingLot(@PathVariable String parkingLotCode,
			@RequestBody ParkingLotCreate parkingLotCreateBody) {

		ParkingLotEntity serviceResponse = parkingLotService.create(
				parkingLotMapper.mapToParkingLotEntityToInsert(parkingLotCode, parkingLotCreateBody));
		return parkingLotMapper.mapToParkingLotCreateResponse(serviceResponse);
	}

	@PutMapping(path = "/parkinglot/{parkingLotCode}", consumes = "application/json", produces = "application/json")
	public ParkingLot updateParkingLot(@PathVariable String parkingLotCode,
			@RequestBody ParkingLotUpdate parkingLotUpdateBody) {

		ParkingLotEntity serviceResponse = parkingLotService.create(
				parkingLotMapper.mapToParkingLotEntityToUpdate(parkingLotCode, parkingLotUpdateBody));
		return parkingLotMapper.mapToParkingLotCreateResponse(serviceResponse);
	}

	@DeleteMapping(path = "/parkinglot/{parkingLotCode}", consumes = "application/json", produces = "application/json")
	public void deleteParkingLot(@PathVariable String parkingLotCode) {
		parkingLotService.delete(parkingLotMapper.mapToParkingLotEntityWithCode(parkingLotCode));
	}
}