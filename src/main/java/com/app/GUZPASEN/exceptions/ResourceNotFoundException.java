package com.app.GUZPASEN.exceptions;

public class ResourceNotFoundException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8791003892741563034L;
	
	public ResourceNotFoundException (String message) {
		super(message);
	}

}
