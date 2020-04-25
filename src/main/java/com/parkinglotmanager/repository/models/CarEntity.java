package com.parkinglotmanager.repository.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "CAR", uniqueConstraints = { @UniqueConstraint(columnNames = { "plate" }) })
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
public class CarEntity extends AbstractBaseEntity {

	@Column
	private String plate;

	@Column
	private Integer type;

	@Temporal(TemporalType.TIMESTAMP)
	private Date arrivalTime;

	@OneToOne
	@JoinColumn(name = "parkinspace_id")
	private ParkingSpaceEntity parkingspace;
}
