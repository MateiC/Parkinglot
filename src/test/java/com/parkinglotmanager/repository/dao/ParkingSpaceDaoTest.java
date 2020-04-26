package com.parkinglotmanager.repository.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.parkinglotmanager.repository.models.CarEntity;
import com.parkinglotmanager.repository.models.ParkingLotEntity;
import com.parkinglotmanager.repository.models.ParkingSpaceEntity;
import com.parkinglotmanager.service.InternalCarTypeEnum;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ParkingSpaceDaoTest {

	@Autowired
	private IParkingSpaceDao testInstance;

	@Autowired
	private IParkingLotDao parkingLotDao;

	@Autowired
	private ICarDao carDao;

	private ParkingSpaceEntity parkingSpace;

	private ParkingLotEntity parkingLot;

	private CarEntity carEntity;

	@Before()
	public void setup() {

		parkingLot = ParkingLotEntity	.builder()
										.code("PL")
										.build();
		// no cascade
		parkingLotDao.saveAndFlush(parkingLot);

		carEntity = CarEntity	.builder()
								.plate("PL1")
								.build();

		parkingSpace = ParkingSpaceEntity	.builder()
											.code("PS1")
											.parkingLot(parkingLot)
											.isOccupied(false)
											.car(carEntity)
											.type(InternalCarTypeEnum.GASOLINE.toString())
											.build();

		testInstance.saveAndFlush(parkingSpace);
	}

	@Test
	public void testSaveAndFlushAndFindOneById() {

		Optional<ParkingSpaceEntity> persistedEntityById = testInstance.findById(parkingSpace.getId());
		assertNotNull(persistedEntityById.get());
	}

	@Test
	public void testCountByTypeWithParkingLotCode() {

		assertEquals(1, testInstance.countFreeSlotsByTypeWithParkingLotCode(InternalCarTypeEnum.GASOLINE.toString(),
				parkingLot.getCode()));
	}

	@Test
	public void testCarCascade() {
		assertEquals(1, carDao.count());
	}
}
