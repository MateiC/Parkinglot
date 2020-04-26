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
		checkIfEntityAlreadyExists(parkingLot);
		createParkingSpaces(parkingLot);
		return parkingLotDao.save(parkingLot);
	}

	@Override
	public ParkingLotEntity retrieve(ParkingLotEntity parkingLot) {
		ParkingLotEntity persistedEntity = retrievePersistedEntity(parkingLot);
		return persistedEntity;
	}

	@Override
	public List<ParkingLotEntity> retrieveAll() {
		return parkingLotDao.findAll();
	}

	@Override
	public ParkingLotEntity update(ParkingLotEntity parkingLot) {
		ParkingLotEntity persistedEntity = retrievePersistedEntity(parkingLot);
		updatePricingPolicy(persistedEntity);
		return parkingLotDao.save(persistedEntity);
	}

	@Override
	public void delete(ParkingLotEntity parkingLot) {
		ParkingLotEntity persistedEntity = retrievePersistedEntity(parkingLot);
		parkingLotDao.delete(persistedEntity);
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

	private List<ParkingSpaceEntity> createParkingSpacesEntityByType(ParkingLotEntity parkingLot, int size,
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

	private int countEntity(String code) {
		return parkingLotDao.countByCode(code);
	}

	private ParkingLotEntity findEntity(String code) {
		return parkingLotDao.findByCode(code);
	}

	private void checkIfEntityAlreadyExists(ParkingLotEntity parkingLot) {
		if (countEntity(parkingLot.getCode()) > 0) {
			throw new ParkingLotManagerException(ErrorsEnum.PARKING_LOT_ALREADY_EXISTS);
		}
	}

	private ParkingLotEntity retrievePersistedEntity(ParkingLotEntity parkingLot) {
		ParkingLotEntity persistedParkingLotEntity = findEntity(parkingLot.getCode());
		if (persistedParkingLotEntity == null) {
			throw new ParkingLotManagerException(ErrorsEnum.PARKING_LOT_NOT_FOUND);
		} else {
			return persistedParkingLotEntity;
		}
	}

	private ParkingLotEntity updatePricingPolicy(ParkingLotEntity persistedLotEntity) {
		PricingPolicyEntity persistentPricingPolicy = persistedLotEntity.getPricingPolicy();
		persistentPricingPolicy.setBasePrice(persistedLotEntity	.getPricingPolicy()
																.getBasePrice());
		persistentPricingPolicy.setType(persistedLotEntity	.getPricingPolicy()
															.getType());
		persistentPricingPolicy.setFixedAmmount(persistedLotEntity	.getPricingPolicy()
																	.getFixedAmmount());
		return persistedLotEntity;

	}
}
