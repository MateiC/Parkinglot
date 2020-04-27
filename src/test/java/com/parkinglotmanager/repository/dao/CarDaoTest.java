package com.parkinglotmanager.repository.dao;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Instant;
import java.util.Date;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.parkinglotmanager.repository.models.CarEntity;
import com.parkinglotmanager.service.InternalCarTypeEnum;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CarDaoTest {

	@Autowired
	private ICarDao testInstance;

	CarEntity carEntity;

	@Before()
	public void setup() {

		carEntity = CarEntity	.builder()
								.plate("PL1")
								.arrivalTime(Date.from(Instant.now()))
								.type(InternalCarTypeEnum.GASOLINE.toString())
								.build();

		testInstance.saveAndFlush(carEntity);
	}

	@Test
	public void testFindById() {
		Optional<CarEntity> persistedEntityById = testInstance.findById(carEntity.getId());
		assertNotNull(persistedEntityById.get());
	}

	@Test
	public void testFindAndCountByPlate() {
		assertNotNull(testInstance.findByPlate(carEntity.getPlate()));
		assertEquals(1, testInstance.countByPlate(carEntity.getPlate()));
	}
}
