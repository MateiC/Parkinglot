package com.parkinglotmanager.repository.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.parkinglotmanager.repository.models.CarEntity;

/**
 * The DAO for the Car entity
 * 
 * @author Mat
 *
 */
@Repository
public interface ICarDao extends JpaRepository<CarEntity, Integer> {

	CarEntity findByPlate(String plate);

	int countByPlate(String plate);

}
