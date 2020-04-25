package com.parkinglotmanager.controller;

import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class ParkingLotControllerTest extends AbstractSpringBootTest {

	@Test
	public void testRetrieveAllParkingLots() {
		RestAssured	.given()
					.contentType(ContentType.JSON)
					.get("/parkinglotmanager/parkinglots")
					.then()
					.assertThat()
					.statusCode(200);
	}

	@Test
	public void testCreateParkingLotByCode() {
//		RestAssured	.given()
//					.contentType(ContentType.JSON)
//					.post("/parkinglotmanager/parkinglot/TES")
//					.then()
//					.assertThat()
//					.statusCode(200);
	}

	@Test
	public void testUpdateParkingLotByCode() {
//		RestAssured	.given()
//					.contentType(ContentType.JSON)
//					.put("/parkinglotmanager/parkinglot/TES")
//					.then()
//					.assertThat()
//					.statusCode(200);
	}

	@Test
	public void testDeleteParkingLotByCode() {
//		RestAssured	.given()
//					.contentType(ContentType.JSON)
//					.delete("/parkinglotmanager/parkinglot/TES")
//					.then()
//					.assertThat()
//					.statusCode(200);
	}
}
