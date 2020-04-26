package com.parkinglotmanager.repository.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

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

	@Test
	public void testSaveAndFlushAndFindOneById() {

		ParkingSpaceEntity parkingSpace = ParkingSpaceEntity.builder()
															.code("PS1")
															.type(InternalCarTypeEnum.GASOLINE.toString())
															.build();

		ParkingSpaceEntity persistedEntity = testInstance.saveAndFlush(parkingSpace);

		Optional<ParkingSpaceEntity> persistedEntityById = testInstance.findById(persistedEntity.getId());

		assertNotNull(persistedEntityById.get());
	}

	@Test
	public void testCountByTypeWithParkingLotCode() {

		ParkingLotEntity parkingLot = ParkingLotEntity	.builder()
														.code("PL")
														.build();
		// no cascade
		parkingLotDao.saveAndFlush(parkingLot);

		ParkingSpaceEntity parkingSpace = ParkingSpaceEntity.builder()
															.code("PS1")
															.parkingLot(parkingLot)
															.isOccupied(false)
															.type(InternalCarTypeEnum.GASOLINE.toString())
															.build();

		testInstance.saveAndFlush(parkingSpace);

		assertEquals(1,
				testInstance.countFreeSlotsByTypeWithParkingLotCode(InternalCarTypeEnum.GASOLINE.toString(), parkingLot.getCode()));
	}
}
