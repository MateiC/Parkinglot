package com.parkinglotmanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.parkinglotmanager.ParkingLot;
import com.parkinglotmanager.ParkingLotCreate;
import com.parkinglotmanager.ParkingLotUpdate;
import com.parkinglotmanager.ParkingLotsRS;
import com.parkinglotmanager.mapper.ParkingLotMapper;
import com.parkinglotmanager.repository.models.ParkingLotEntity;
import com.parkinglotmanager.service.interfaces.IParkingLotService;

@RestController
@RequestMapping("parkinglotmanager")
public class ParkingLotController {

	@Autowired
	IParkingLotService parkingLotService;

	@Autowired
	ParkingLotMapper parkingLotMapper;

	@GetMapping("/parkinglots")
	public ParkingLotsRS retrieveAllParkingLots() {
		return parkingLotMapper.mapToRetrieveAll(parkingLotService.retrieveAll());
	}

	@GetMapping(path = "/parkinglot/{parkingLotCode}", produces = "application/json")
	public ParkingLot retrieveParkingLot(@PathVariable String parkingLotCode) {
		return parkingLotMapper.mapToParkingLotRetrieve(
				parkingLotService.retrieve(parkingLotMapper.mapToParkingLotEntityWithCode(parkingLotCode)));
	}

	@PostMapping(path = "/parkinglot/{parkingLotCode}", consumes = "application/json", produces = "application/json")
	public ParkingLotCreate createParkingLot(@PathVariable String parkingLotCode,
			@RequestBody ParkingLotCreate parkingLotCreateBody) {

		ParkingLotEntity serviceResponse = parkingLotService.create(
				parkingLotMapper.mapToParkingLotEntityToCreate(parkingLotCode, parkingLotCreateBody));
		return parkingLotMapper.mapToParkingLotCreate(serviceResponse);
	}

	@PutMapping(path = "/parkinglot/{parkingLotCode}", consumes = "application/json", produces = "application/json")
	public ParkingLotUpdate updateParkingLot(@PathVariable String parkingLotCode,
			@RequestBody ParkingLotUpdate parkingLotUpdateBody) {

		ParkingLotEntity serviceResponse = parkingLotService.update(
				parkingLotMapper.mapToParkingLotEntityToUpdate(parkingLotCode, parkingLotUpdateBody));
		return parkingLotMapper.mapToParkingLotUpdate(serviceResponse);
	}

	@DeleteMapping(path = "/parkinglot/{parkingLotCode}", consumes = "application/json", produces = "application/json")
	public void deleteParkingLot(@PathVariable String parkingLotCode, @RequestParam("version") Integer version) {
		parkingLotService.delete(parkingLotMapper.mapToParkingLotEntityToDelete(parkingLotCode, version));
	}
}
