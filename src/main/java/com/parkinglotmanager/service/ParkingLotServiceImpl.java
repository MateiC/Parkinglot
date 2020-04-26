package com.parkinglotmanager.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.parkinglotmanager.error.ErrorsEnum;
import com.parkinglotmanager.error.ParkingLotManagerException;
import com.parkinglotmanager.repository.dao.IParkingLotDao;
import com.parkinglotmanager.repository.dao.IParkingSpaceDao;
import com.parkinglotmanager.repository.dao.IPricingPolicyDao;
import com.parkinglotmanager.repository.models.ParkingLotEntity;
import com.parkinglotmanager.repository.models.ParkingSpaceEntity;
import com.parkinglotmanager.repository.models.PricingPolicyEntity;
import com.parkinglotmanager.service.interfaces.IParkingLotService;

@Service
@Transactional
public class ParkingLotServiceImpl implements IParkingLotService {

	@Autowired
	IParkingLotDao parkingLotDao;

	@Autowired
	IParkingSpaceDao parkingSpaceDao;

	@Autowired
	IPricingPolicyDao pricingPolicyDao;

	@Override
	public ParkingLotEntity create(ParkingLotEntity parkingLot) {
		checkIfEntityExists(parkingLot);
		createParkingSpaces(parkingLot);
		return parkingLotDao.save(parkingLot);
	}

	@Override
	public ParkingLotEntity retrieve(ParkingLotEntity parkingLot) {
		checkIfEntityDoesNotExist(parkingLot);
		return parkingLotDao.findByCode(parkingLot.getCode());
	}

	@Override
	public List<ParkingLotEntity> retrieveAll() {
		return parkingLotDao.findAll();
	}

	@Override
	public ParkingLotEntity update(ParkingLotEntity parkingLot) {
		checkIfEntityDoesNotExist(parkingLot);
		ParkingLotEntity persistedEntity = retrieveAndUpdatePricingPolicy(parkingLot);
		return parkingLotDao.save(persistedEntity);
	}

	@Override
	public void delete(ParkingLotEntity parkingLot) {
		checkIfEntityDoesNotExist(parkingLot);
		ParkingLotEntity foundEntity = parkingLotDao.findByCode(parkingLot.getCode());
		parkingLotDao.delete(foundEntity);
	}

	private void createParkingSpaces(ParkingLotEntity parkingLot) {
		parkingLot	.getParkingSpaces()
					.addAll(createParkingSpacesEntityByType(parkingLot, parkingLot.getGasolineSlots(),
							InternalCarTypeEnum.GASOLINE));
		parkingLot	.getParkingSpaces()
					.addAll(createParkingSpacesEntityByType(parkingLot, parkingLot.getSmallKwSlots(),
							InternalCarTypeEnum.SMALLKW));
		parkingLot	.getParkingSpaces()
					.addAll(createParkingSpacesEntityByType(parkingLot, parkingLot.getBigKwSlots(),
							InternalCarTypeEnum.BIGKW));

	}

	List<ParkingSpaceEntity> createParkingSpacesEntityByType(ParkingLotEntity parkingLot, int size,
			InternalCarTypeEnum type) {
		ArrayList<ParkingSpaceEntity> result = new ArrayList<ParkingSpaceEntity>();
		for (int i = 0; i < size; i++) {
			result.add(ParkingSpaceEntity	.builder()
											.code(type.toString() + i)
											.type(type.toString())
											.parkingLot(parkingLot)
											.isOccupied(false)
											.build());
		}
		return result;
	}

	void checkIfEntityExists(ParkingLotEntity parkingLot) {
		if (parkingLotDao.findByCode(parkingLot.getCode()) != null) {
			throw new ParkingLotManagerException(ErrorsEnum.PARKING_LOT_ALREADY_EXISTS);
		}
	}

	void checkIfEntityDoesNotExist(ParkingLotEntity parkingLot) {
		if (parkingLotDao.findByCode(parkingLot.getCode()) == null) {
			throw new ParkingLotManagerException(ErrorsEnum.PARKING_LOT_NOT_FOUND);
		}
	}

	private ParkingLotEntity retrieveAndUpdatePricingPolicy(ParkingLotEntity parkingLot) {
		ParkingLotEntity persistedLotEntity = parkingLotDao.findByCode(parkingLot.getCode());
		PricingPolicyEntity persistentPricingPolicy = persistedLotEntity.getPricingPolicy();
		persistentPricingPolicy.setBasePrice(parkingLot	.getPricingPolicy()
														.getBasePrice());
		persistentPricingPolicy.setType(parkingLot	.getPricingPolicy()
													.getType());
		persistentPricingPolicy.setFixedAmmount(parkingLot	.getPricingPolicy()
															.getFixedAmmount());
		return persistedLotEntity;

	}
}
