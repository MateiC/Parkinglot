package com.parkinglotmanager.service;

public enum InternalPricingPolicyEnum {
	PER_HOUR("H"), PER_HOUR_WITH_FIXED_AMMOUNT("HF");

	private final String value;

	private InternalPricingPolicyEnum(final String value) {
		this.value = value;
	}

	public static InternalPricingPolicyEnum fromString(final String text) {
		for (InternalPricingPolicyEnum element : InternalPricingPolicyEnum.values()) {
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