package com.parkinglotmanager.service;

public enum ParkingSlotType {
	GASOLINE("G"), SMALLKW("S"), BIGKW("B");

	private final String value;

	private ParkingSlotType(final String value) {
		this.value = value;
	}

	public static ParkingSlotType fromString(final String text) {
		for (ParkingSlotType element : ParkingSlotType.values()) {
			if (element.value.equalsIgnoreCase(text)) {
				return element;
			}
		}
		return null;
	}

	@Override
	public String toString() {
		return this.value;
	}
}