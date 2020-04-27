package com.parkinglotmanager.repository.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "PRICING_POLICY", uniqueConstraints = {
		@UniqueConstraint(columnNames = { "type", "basePrice", "fixedAmmount" }) })
@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class PricingPolicyEntity extends AbstractBaseEntity {

	@Id
	@GeneratedValue
	@Column(nullable = false, insertable = false, updatable = false)
	protected Integer id;

	@Column
	private String type;

	@Column
	private Float basePrice;

	@Column
	private Float fixedAmmount;
}
