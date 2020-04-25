package com.parkinglotmanager.service.interfaces;

import java.util.List;

import com.parkinglotmanager.repository.models.ParkingLotEntity;

public interface ICreateDeleteService {

	ParkingLotEntity create(ParkingLotEntity parkingLot);

	ParkingLotEntity update(ParkingLotEntity parkingLot);

	ParkingLotEntity retrieve(ParkingLotEntity parkingLot);

	List<ParkingLotEntity> retrieveAll();

	void delete(ParkingLotEntity parkingLot);
}
