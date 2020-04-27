package com.parkinglotmanager.repository.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "PARKING_LOT", uniqueConstraints = { @UniqueConstraint(columnNames = { "code" }) })
@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class ParkingLotEntity extends AbstractBaseEntity {

	@Column
	private String code;

	@Column
	private String description;

	@Column
	private Integer gasolineSlots;

	@Column
	private Integer smallKwSlots;

	@Column
	private Integer bigKwSlots;

	@Version
	@Column(name = "version", nullable = false)
	protected Integer version;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "pricingpolicy_id")
	private PricingPolicyEntity pricingPolicy;

	@Builder.Default
	@OneToMany(mappedBy = "parkingLot", cascade = CascadeType.ALL)
	private List<ParkingSpaceEntity> parkingSpaces = new ArrayList<ParkingSpaceEntity>();
}
