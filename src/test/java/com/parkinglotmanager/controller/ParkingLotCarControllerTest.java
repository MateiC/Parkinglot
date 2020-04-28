package com.parkinglotmanager.controller;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ParkingLotCarControllerTest extends AbstractSpringBootTest {

	@Test
	public void A_testCreateParkingLotByCode() {
		RestAssured	.given()
					.contentType(ContentType.JSON)
					// @formatter:off
					.body("{\r\n" + "  \"description\": \"Nice Centre\",\r\n"
							+ "  \"numberOfStandardParkingSlots\": 1,\r\n" 
							+ "  \"numberOf20KWParkingSlots\": 0,\r\n"
							+ "  \"numberOf50KWParkingSlots\": 0,\r\n" 
							+ "  \"pricingPolicy\": {\r\n"
							+ "    \"policy\": \"perHour\",\r\n" 
							+ "    \"basePrice\": 0,\r\n"
							+ "    \"fixedAmmount\": 0\r\n" 
							+ "  }\r\n" 
							+ "}")
					// @formatter:on
					.post("/parkinglotmanager/parkinglot/TES")
					.then()
					.assertThat()
					.statusCode(200);
	}

	@Test
	public void B_testCreateParkingLotEntryByCode() {
		RestAssured	.given()
					.contentType(ContentType.JSON)
					.post("/parkinglotmanager/parkinglot/TES/carParked/BB333EQ?carType=GASOLINE")
					.then()
					.assertThat()
					.statusCode(200);
	}

	@Test
	public void C_testUpdateParkingLotEntryByCode() {
		RestAssured	.given()
					.contentType(ContentType.JSON)
					.delete("/parkinglotmanager/parkinglot/TES/carParked/BB333EQ")
					.then()
					.assertThat()
					.statusCode(200);
	}

	@Test
	public void D_testDeleteParkingLotByCode() {
		RestAssured	.given()
					.contentType(ContentType.JSON)
					.delete("/parkinglotmanager/parkinglot/TES?version=0")
					.then()
					.assertThat()
					.statusCode(200);
	}
}
