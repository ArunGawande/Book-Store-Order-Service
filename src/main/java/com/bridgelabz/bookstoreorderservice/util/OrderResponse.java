package com.bridgelabz.bookstoreorderservice.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponse {
	private int statusCode;
	private String message;
	private Object object;
}
