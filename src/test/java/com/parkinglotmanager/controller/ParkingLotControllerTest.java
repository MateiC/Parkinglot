package com.parkinglotmanager.controller;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ParkingLotControllerTest extends AbstractSpringBootTest {

	@Test
	public void A_testRetrieveAllParkingLots() {
		RestAssured	.given()
					.contentType(ContentType.JSON)
					.get("/parkinglotmanager/parkinglots")
					.then()
					.assertThat()
					.statusCode(200);
	}

	@Test

	public void B_testCreateParkingLotByCode() {
		RestAssured	.given()
					.contentType(ContentType.JSON)
					// @formatter:off
					.body("{\r\n" + "  \"description\": \"Nice Centre\",\r\n"
							+ "  \"numberOfStandardParkingSlots\": 0,\r\n" 
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
	public void C_testRetrieveParkingLotByCode() {
		RestAssured	.given()
					.contentType(ContentType.JSON)
					.get("/parkinglotmanager/parkinglot/TES")
					.then()
					.assertThat()
					.statusCode(200);
	}

	@Test
	public void D_testRetrieveParkingLotByCodeWithInvalid() {
		RestAssured	.given()
					.contentType(ContentType.JSON)
					.get("/parkinglotmanager/parkinglot/TE1")
					.then()
					.assertThat()
					.statusCode(500);
	}

	@Test
	public void E_testUpdateParkingLotByCode() {
		RestAssured	.given()
					.contentType(ContentType.JSON)
					// @formatter:off
					.body("{\r\n" + "  \"pricingPolicy\": {\r\n" 
							+ "    \"policy\": \"perHour\",\r\n"
							+ "    \"basePrice\": 2,\r\n" + "    \"fixedAmmount\": 1\r\n" 
							+ "  },\r\n"
							+ "  \"version\": 0\r\n" 
							+ "}")
					// @formatter:on
					.put("/parkinglotmanager/parkinglot/TES")
					.then()
					.assertThat()
					.statusCode(200);
	}

	@Test
	public void F_testDeleteParkingLotByCode() {
		RestAssured	.given()
					.contentType(ContentType.JSON)
					.delete("/parkinglotmanager/parkinglot/TES?version=1")
					.then()
					.assertThat()
					.statusCode(200);
	}
}
