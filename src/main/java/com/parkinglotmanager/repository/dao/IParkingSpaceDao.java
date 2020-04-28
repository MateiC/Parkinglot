package com.parkinglotmanager.repository.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.parkinglotmanager.repository.models.ParkingSpaceEntity;

/**
 * The DAO for the Parking space entity
 * 
 * @author Mat
 *
 */
@Repository
public interface IParkingSpaceDao extends JpaRepository<ParkingSpaceEntity, Integer> {

	@Query("SELECT COUNT(p.id) FROM ParkingSpaceEntity p WHERE p.type = :type AND p.parkingLot.code = :parkingLotCode AND p.isOccupied is false")
	int countFreeSlotsByTypeWithParkingLotCode(@Param("type") String type,
			@Param("parkingLotCode") String parkingLotCode);

	@Query("SELECT p FROM ParkingSpaceEntity p WHERE p.type = :type AND p.parkingLot.code = :parkingLotCode AND p.isOccupied is false")
	List<ParkingSpaceEntity> findFreeSlotsByTypeWithParkingLotCode(@Param("type") String type,
			@Param("parkingLotCode") String parkingLotCode);

}
