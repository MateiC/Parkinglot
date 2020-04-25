package com.parkinglotmanager.repository.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.parkinglotmanager.repository.models.CarEntity;

@Repository
public interface ICarDao extends JpaRepository<CarEntity, Integer> {

}