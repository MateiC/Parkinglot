package com.parkinglotmanager.repository.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.parkinglotmanager.repository.models.ParkingSpaceEntity;

@Repository
public interface IParkingSpaceDao extends JpaRepository<ParkingSpaceEntity, Integer> {

}
