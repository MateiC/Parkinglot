package com.parkinglotmanager.service;

import java.time.Instant;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.parkinglotmanager.error.ErrorsEnum;
import com.parkinglotmanager.error.ParkingLotManagerException;
import com.parkinglotmanager.repository.dao.ICarDao;
import com.parkinglotmanager.repository.dao.IParkingLotDao;
import com.parkinglotmanager.repository.dao.IParkingSpaceDao;
import com.parkinglotmanager.repository.dao.IPricingPolicyDao;
import com.parkinglotmanager.repository.models.CarEntity;
import com.parkinglotmanager.repository.models.ParkingLotEntity;
import com.parkinglotmanager.repository.models.ParkingSpaceEntity;
import com.parkinglotmanager.repository.models.PricingPolicyEntity;
import com.parkinglotmanager.service.interfaces.IParkingLotCarService;

/**
 * Service layer for the car
 * 
 * @author Mat
 *
 */
@Service
@Transactional
public class ParkingLotCarServiceImpl implements IParkingLotCarService {

	@Autowired
	ICarDao carDao;

	@Autowired
	IParkingLotDao parkingLotDao;

	@Autowired
	IParkingSpaceDao parkingSpaceDao;

	@Autowired
	IPricingPolicyDao pricingPolicyDao;

	@Override
	public CarBom create(CarBom carBom) {
		checkIfParkingLotDoesNotExist(carBom.getParkingLotCode());
		checkIfEntityExists(carBom.getCarEntity());
		assignFreeSpace(carBom);
		carBom.setCarEntity(carDao.saveAndFlush(carBom.getCarEntity()));
		return carBom;
	}

	@Override
	public CarBom delete(CarBom carBom) {
		checkIfParkingLotDoesNotExist(carBom.getParkingLotCode());
		CarEntity persistedCarEntity = retrievePersistedEntity(carBom.getCarEntity());
		computePrice(carBom);
		unassignSpace(carBom, persistedCarEntity);
		carDao.delete(persistedCarEntity);
		return carBom;
	}

	private int countEntity(String plate) {
		return carDao.countByPlate(plate);
	}

	private CarEntity findEntity(String plate) {
		return carDao.findByPlate(plate);
	}

	private void checkIfEntityExists(CarEntity carEntity) {
		if (countEntity(carEntity.getPlate()) > 0) {
			throw new ParkingLotManagerException(ErrorsEnum.CAR_ALREADY_EXISTS);
		}
	}

	private CarEntity retrievePersistedEntity(CarEntity carEntity) {
		CarEntity persistedCarEntity = findEntity(carEntity.getPlate());
		if (persistedCarEntity == null) {
			throw new ParkingLotManagerException(ErrorsEnum.CAR_NOT_FOUND);
		} else {
			return persistedCarEntity;
		}
	}

	private void checkIfParkingLotDoesNotExist(String code) {
		if (parkingLotDao.findByCode(code) == null) {
			throw new ParkingLotManagerException(ErrorsEnum.PARKING_LOT_NOT_FOUND);
		}
	}

	private void assignFreeSpace(CarBom carBom) {
		CarEntity carEntity = carBom.getCarEntity();
		if (parkingSpaceDao.countFreeSlotsByTypeWithParkingLotCode(carEntity.getType(),
				carBom.getParkingLotCode()) != 0) {
			ParkingSpaceEntity vacantParkingSpace = parkingSpaceDao	.findFreeSlotsByTypeWithParkingLotCode(
																			carEntity.getType(),
																			carBom.getParkingLotCode())
																	.get(0);
			carEntity.setParkingSpace(vacantParkingSpace);
			vacantParkingSpace.setIsOccupied(true);
			carBom.setParkingSpaceCode(vacantParkingSpace.getCode());
		} else {
			throw new ParkingLotManagerException(ErrorsEnum.NOT_FREE_SLOT_FOR_TYPE);
		}
	}

	private void unassignSpace(CarBom carBom, CarEntity persistedCarEntity) {
		ParkingSpaceEntity persistedParkingSpace = persistedCarEntity.getParkingSpace();
		persistedParkingSpace.setCar(null);
		persistedParkingSpace.setIsOccupied(false);
		persistedCarEntity.setParkingSpace(null);
		carBom.setParkingSpaceCode(persistedParkingSpace.getCode());
	}

	private void computePrice(CarBom carBom) {
		ParkingLotEntity persistedParkingLot = parkingLotDao.findByCode(carBom.getParkingLotCode());
		PricingPolicyEntity persistedPricingPolicy = persistedParkingLot.getPricingPolicy();
		CarEntity persisteCarEntity = findEntity(carBom	.getCarEntity()
														.getPlate());

		long diffHours = computeDiffInHours(persisteCarEntity);
		switch (InternalPricingPolicyEnum.fromString(persistedPricingPolicy.getType())) {
		case PER_HOUR:
			carBom.setPriceToPay(diffHours * persistedPricingPolicy.getBasePrice());
			break;
		case PER_HOUR_WITH_FIXED_AMMOUNT:
			carBom.setPriceToPay(
					persistedPricingPolicy.getFixedAmmount() + diffHours * persistedPricingPolicy.getBasePrice());
			break;
		default:
			throw new ParkingLotManagerException(ErrorsEnum.UNKNOWN_PRICING_POLICY_TYPE);

		}
	}

	private long computeDiffInHours(CarEntity persisteCarEntity) {
		long diff = Date.from(Instant.now())
						.getTime()
				- persisteCarEntity	.getArrivalTime()
									.getTime();
		long diffHours = diff / (60 * 60 * 1000) % 24;
		return diffHours;
	}

}
