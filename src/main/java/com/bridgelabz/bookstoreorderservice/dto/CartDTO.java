package com.bridgelabz.bookstoreorderservice.dto;

import lombok.Data;

@Data
public class CartDTO {
	private long cartId;
    private long userId;
    private long bookId;
    private int quantity;
    private int totalPrice;
}
