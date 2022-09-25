package com.bridgelabz.bookstoreorderservice.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus
public class OrderDetailsNotFoundException extends RuntimeException{
	private int statusCode;
	private String message;
	
	public OrderDetailsNotFoundException(int statusCode, String message) {
		super();
		this.statusCode = statusCode;
		this.message = message;
	}
	
}
