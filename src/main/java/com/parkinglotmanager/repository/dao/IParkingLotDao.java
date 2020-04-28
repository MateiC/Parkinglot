package com.parkinglotmanager.repository.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.parkinglotmanager.repository.models.ParkingLotEntity;

/**
 * The DAO for the Parking lot entity
 * 
 * @author Mat
 *
 */
@Repository
public interface IParkingLotDao extends JpaRepository<ParkingLotEntity, Integer> {

	ParkingLotEntity findByCode(String code);

	int countByCode(String code);

	void deleteByCode(String code);
}
