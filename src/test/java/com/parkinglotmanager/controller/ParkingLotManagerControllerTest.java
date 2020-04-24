package com.parkinglotmanager.controller;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.context.WebApplicationContext;

import io.restassured.RestAssured;
import io.restassured.module.mockmvc.RestAssuredMockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class ParkingLotManagerControllerTest {

	@LocalServerPort
	private int port;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@BeforeClass
	public void beforeClass() {
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
	}

	@Before
	public void setup() {
		RestAssured.port = port;
		RestAssuredMockMvc.webAppContextSetup(webApplicationContext);
	}

	@Test
	public void testRetrieveAllParkingLots() {
		RestAssured.get("/parkinglotmanager/parkinglots").then().assertThat().statusCode(200);
	}

	@Test
	public void testCreateParkingLotByCode() {
		RestAssured.post("/parkinglotmanager/parkinglots/TES").then().assertThat().statusCode(200);
	}

	@Test
	public void testUpdateParkingLotByCode() {
		RestAssured.put("/parkinglotmanager/parkinglots/TES").then().assertThat().statusCode(200);
	}

	@Test
	public void testDeleteParkingLotByCode() {
		RestAssured.delete("/parkinglotmanager/parkinglots/TES").then().assertThat().statusCode(200);
	}

	@Test
	public void testCreateParkingLotEntryByCode() {
		RestAssured.get("/parkinglotmanager/parkinglots/TES/carParked").then().assertThat().statusCode(200);
	}

	@Test
	public void testUpdateParkingLotEntryByCode() {
		RestAssured.get("/parkinglotmanager/parkinglots/TES/carParked").then().assertThat().statusCode(200);
	}
}
