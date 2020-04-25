package com.parkinglotmanager.repository.dao;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.parkinglotmanager.repository.models.ParkingLotEntity;
import com.parkinglotmanager.repository.models.ParkingSpaceEntity;
import com.parkinglotmanager.service.ParkingSlotType;

@RunWith(SpringRunner.class)
@DataJpaTest
public class IParkingLotDaoTest {

	@Autowired
	private IParkingLotDao testInstance;

	@Test
	public void testSaveAndFlushAndFindOneById() {
		ParkingLotEntity persistedEntity = testInstance.saveAndFlush(ParkingLotEntity	.builder()
																						.code("PKG")
																						.description("Parking lot")
																						.gasolineSlots(1)
																						.smallKwSlots(2)
																						.bigKwSlots(3)
																						.build());

		Optional<ParkingLotEntity> persistedEntityById = testInstance.findById(persistedEntity.getId());

		assertNotNull(persistedEntityById.get());
	}

	@Test
	public void testSaveWithParkingSpace() {
		ParkingSpaceEntity parkingSpace = ParkingSpaceEntity.builder()
															.code("PS1")
															/* .isOccupied(true) */
															.type(ParkingSlotType.GASOLINE.toString())
															.build();
		ParkingLotEntity parkingLotEntity = ParkingLotEntity.builder()
															.code("PKG")
															.description("Parking lot")
															.gasolineSlots(1)
															.smallKwSlots(2)
															.bigKwSlots(3)
															.parkingSpaces(Arrays.asList(parkingSpace))
															.build();

		ParkingLotEntity persistedEntity = testInstance.saveAndFlush(parkingLotEntity);

		assertNotNull(persistedEntity);
		assertEquals(1, persistedEntity	.getParkingSpaces()
										.size());
	}
}
