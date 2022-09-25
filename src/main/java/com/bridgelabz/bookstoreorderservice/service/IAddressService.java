package com.bridgelabz.bookstoreorderservice.service;

import com.bridgelabz.bookstoreorderservice.dto.AddressDTO;
import com.bridgelabz.bookstoreorderservice.model.AddressModel;

import java.util.List;

public interface IAddressService {

	AddressModel inserAddress(AddressDTO addressDTO, String token);

	AddressModel updateAddress(Long addressId, AddressDTO addressDTO, String token);

	List<AddressModel>  fetchAllAddresses(String token);

	AddressModel getAddressById(Long addressId, String token);

	AddressModel deleteAddressById(Long addressId, String token);

}
