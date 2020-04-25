package com.parkinglotmanager.controller;

import org.junit.Before;
import org.junit.BeforeClass;
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
public abstract class AbstractSpringBootTest {

	@LocalServerPort
	private int port;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@BeforeClass
	public static void beforeClass() {
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
	}

	@Before
	public void setup() {
		RestAssured.port = port;
		RestAssuredMockMvc.webAppContextSetup(webApplicationContext);
	}
}
