package com.parkinglotmanager.service;

import com.parkinglotmanager.repository.models.CarEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * BOM used to pass more info from the repository to the service layer and back.
 * 
 * @author Mat
 *
 */
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
