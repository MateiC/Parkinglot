package com.parkinglotmanager.service;

import com.parkinglotmanager.repository.models.CarEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CarBom {

	private CarEntity carEntity;

	private String parkingLotCode;

	private Float priceToPay;

	private String parkingSpaceCode;
}
