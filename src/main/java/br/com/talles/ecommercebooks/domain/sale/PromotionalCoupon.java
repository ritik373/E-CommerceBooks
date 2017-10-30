package br.com.talles.ecommercebooks.domain.sale;

import br.com.talles.ecommercebooks.domain.Entity;

public class PromotionalCoupon extends Entity {

	// Attributes
	private String code;
	private double value;

	// Constructors
	public PromotionalCoupon() { }

	public PromotionalCoupon(String code, double value) {
		this.code = code;
		this.value = value;
	}

	// Getters
	public double getValue() {
		return value;
	}

	public String getCode() {
		return code;
	}
	
	// Setters
	public void setValue(double value) {
		this.value = value;
	}

	public void setCode(String code) {
		this.code = code;
	}

	

}
