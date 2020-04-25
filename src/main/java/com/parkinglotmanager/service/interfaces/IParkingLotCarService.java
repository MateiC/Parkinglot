package com.parkinglotmanager.service.interfaces;

import com.parkinglotmanager.repository.models.CarEntity;

public interface IParkingLotCarService {

	CarEntity create(CarEntity parkingLot);

	CarEntity delete(CarEntity parkingLot);
}
