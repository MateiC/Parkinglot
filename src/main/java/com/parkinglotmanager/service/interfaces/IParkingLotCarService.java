package com.parkinglotmanager.service.interfaces;

import com.parkinglotmanager.service.CarBom;

public interface IParkingLotCarService {

	CarBom create(CarBom carBom);

	CarBom delete(CarBom carBom);
}
