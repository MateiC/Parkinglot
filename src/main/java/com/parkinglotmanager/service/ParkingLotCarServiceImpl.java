package com.parkinglotmanager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.parkinglotmanager.error.Errors;
import com.parkinglotmanager.error.ParkingLotManagerException;
import com.parkinglotmanager.repository.dao.ICarDao;
import com.parkinglotmanager.repository.models.CarEntity;
import com.parkinglotmanager.service.interfaces.IParkingLotCarService;

@Service
@Transactional
public class ParkingLotCarServiceImpl implements IParkingLotCarService {

	@Autowired
	ICarDao carDao;

	@Override
	public CarEntity create(CarEntity parkingLot) {
		checkIfEntityExists(parkingLot);
		return carDao.save(parkingLot);
	}

	@Override
	public CarEntity delete(CarEntity parkingLot) {
		return null;
	}

	void checkIfEntityExists(CarEntity parkingLot) {
		if (carDao.findByPlate(parkingLot.getPlate()) != null) {
			throw new ParkingLotManagerException(Errors.ALREADY_EXISTS);
		}
	}

	void checkIfEntityDoesNotExist(CarEntity parkingLot) {
		if (carDao.findByPlate(parkingLot.getPlate()) == null) {
			throw new ParkingLotManagerException(Errors.NOT_FOUND);
		}
	}
}
