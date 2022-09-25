package com.bridgelabz.bookstoreorderservice.dto;

import lombok.Data;

import javax.persistence.Lob;


@Data
public class BookServiceDTO {
	private long bookId;
    private String bookName;
    private String bookAuthor;
    private String bookDescription;  
    @Lob
	private byte[] bookLogo;
    private int bookPrice;
    private int bookQuantity;
}
