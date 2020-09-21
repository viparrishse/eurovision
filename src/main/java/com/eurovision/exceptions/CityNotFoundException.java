package com.eurovision.exceptions;

public class CityNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CityNotFoundException(String exception) {
		super(exception);
	}

}