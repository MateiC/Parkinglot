package com.parkinglotmanager.repository.dao;

import static org.junit.Assert.assertNotNull;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.parkinglotmanager.repository.models.ParkingSpaceEntity;
import com.parkinglotmanager.service.ParkingSlotType;

@RunWith(SpringRunner.class)
@DataJpaTest
public class IParkingSpaceDaoTest {

	@Autowired
	private IParkingSpaceDao testInstance;

	@Test
	public void testSaveAndFlushAndFindOneById() {

		ParkingSpaceEntity parkingSpace = ParkingSpaceEntity.builder()
															.code("PS1")
															/* .isOccupied(true) */
															.type(ParkingSlotType.GASOLINE.toString())
															.build();

		ParkingSpaceEntity persistedEntity = testInstance.saveAndFlush(parkingSpace);

		Optional<ParkingSpaceEntity> persistedEntityById = testInstance.findById(persistedEntity.getId());

		assertNotNull(persistedEntityById.get());
	}
}
