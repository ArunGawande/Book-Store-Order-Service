package com.bridgelabz.bookstoreorderservice.dto;

import lombok.Data;

@Data
public class AddressDTO {
	public String name;
	public long phoneNumber;
	public long pincode; 
	public String locality;
	public String address;
	private String city;
	private String landmark;
}
