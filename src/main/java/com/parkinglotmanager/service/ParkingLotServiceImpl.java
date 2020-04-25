package com.parkinglotmanager.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.parkinglotmanager.repository.dao.IParkingLotDao;
import com.parkinglotmanager.repository.models.ParkingLotEntity;
import com.parkinglotmanager.service.interfaces.IParkingLotService;

@Service
@Transactional
public class ParkingLotServiceImpl extends AbstractBaseService implements IParkingLotService {

	@Autowired
	IParkingLotDao parkingLotDao;

	@Override
	public ParkingLotEntity create(ParkingLotEntity parkingLot) {
		return parkingLotDao.save(parkingLot);
	}

	@Override
	public ParkingLotEntity retrieve(ParkingLotEntity parkingLot) {
		return parkingLotDao.findByCode(parkingLot.getCode());
	}

	@Override
	public List<ParkingLotEntity> retrieveAll() {
		return parkingLotDao.findAll();
	}

	@Override
	public ParkingLotEntity update(ParkingLotEntity parkingLot) {
		return parkingLotDao.save(parkingLot);
	}

	@Override
	public void delete(ParkingLotEntity parkingLot) {
		ParkingLotEntity foundEntity = parkingLotDao.findByCode(parkingLot.getCode());
		parkingLotDao.delete(foundEntity);
	}
}
