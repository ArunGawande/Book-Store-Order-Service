package com.bridgelabz.bookstoreorderservice.service;

import com.bridgelabz.bookstoreorderservice.dto.AddressDTO;
import com.bridgelabz.bookstoreorderservice.exception.OrderDetailsNotFoundException;
import com.bridgelabz.bookstoreorderservice.model.AddressModel;
import com.bridgelabz.bookstoreorderservice.repository.AddressRepository;
import com.bridgelabz.bookstoreorderservice.util.TokenUtil;
import com.bridgelabz.bookstoreorderservice.util.UserResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class AddressService implements IAddressService{
	@Autowired
	AddressRepository addressRepository;
	@Autowired
	TokenUtil tokenUtil;
	@Autowired
	RestTemplate restTemplate;


	@Override
	public AddressModel inserAddress(AddressDTO addressDTO, String token) {
		Long userId = tokenUtil.decodeToken(token);
		UserResponse isUserPresent = restTemplate.getForObject("http://BOOKSTORE-USERSERVICE:8081/bookstoreuser/validateuser/" + userId, UserResponse.class);
		if (isUserPresent.getErrorCode() == 200) {
			AddressModel addressModel = new AddressModel(addressDTO);
			addressModel.setUserId(userId);
			addressRepository.save(addressModel);
			return addressModel;
		}
		throw new OrderDetailsNotFoundException(500, "User Not Found");
	}


	@Override
	public AddressModel updateAddress(Long addressId, AddressDTO addressDTO, String token) {
		Long userId = tokenUtil.decodeToken(token);
		UserResponse isUserPresent = restTemplate.getForObject("http://BOOKSTORE-USERSERVICE:8081/bookstoreuser/validateuser/" + userId, UserResponse.class);
		if (isUserPresent.getErrorCode() == 200) {
			Optional<AddressModel> isAddressPresent = addressRepository.findById(addressId);
			if (isAddressPresent.isPresent()) {
				if (isAddressPresent.get().getUserId() == userId) {
					isAddressPresent.get().setName(addressDTO.getName());
					isAddressPresent.get().setPhoneNumber(addressDTO.getPhoneNumber());
					isAddressPresent.get().setAddress(addressDTO.getAddress());
					isAddressPresent.get().setPincode(addressDTO.getPincode());
					isAddressPresent.get().setLocality(addressDTO.getLocality());
					isAddressPresent.get().setLandmark(addressDTO.getLandmark());
					isAddressPresent.get().setCity(addressDTO.getCity());
					addressRepository.save(isAddressPresent.get());
					return isAddressPresent.get();
				}
				throw new OrderDetailsNotFoundException(500, "Invalid User");
			}
			throw new OrderDetailsNotFoundException(500, "Address Not Found");
		}
		throw new OrderDetailsNotFoundException(500, "User Not Found");
	}


	@Override
	public List<AddressModel> fetchAllAddresses(String token) {
		Long userId = tokenUtil.decodeToken(token);
		UserResponse isUserPresent = restTemplate.getForObject("http://BOOKSTORE-USERSERVICE:8081/bookstoreuser/validateuser/" + userId, UserResponse.class);
		if (isUserPresent.getErrorCode() == 200) {
			List<AddressModel> isAddressPresent = addressRepository.findAll();
			if (isAddressPresent.size()>0) {
				return isAddressPresent;
			}
			throw new OrderDetailsNotFoundException(500, "Empty Address List");
		}
		throw new OrderDetailsNotFoundException(500, "User Not Found");
	}

	@Override
	public AddressModel deleteAddressById(Long addressId, String token) {
		Long userId = tokenUtil.decodeToken(token);
		UserResponse isUserPresent = restTemplate.getForObject("http://BOOKSTORE-USERSERVICE:8081/bookstoreuser/validateuser/" + userId, UserResponse.class);
		if (isUserPresent.getErrorCode() == 200) {
			Optional<AddressModel> isAddressPresent = addressRepository.findById(addressId);
			if (isAddressPresent.isPresent()) {
				if (isAddressPresent.get().getUserId() == userId) {
					addressRepository.delete(isAddressPresent.get());
					return isAddressPresent.get();
				}
			}
			throw new OrderDetailsNotFoundException(500, "Address Not Found");
		}
		throw new OrderDetailsNotFoundException(500, "User Not Found");

	}

	@Override
	public AddressModel getAddressById(Long addressId, String token) {
		Long userId = tokenUtil.decodeToken(token);
		UserResponse isUserPresent = restTemplate.getForObject("http://BOOKSTORE-USERSERVICE:8081/bookstoreuser/validateuser/" + userId, UserResponse.class);
		if (isUserPresent.getErrorCode() == 200) {
			Optional<AddressModel> isAddressPresent = addressRepository.findById(addressId);
			if (isAddressPresent.isPresent()) {
				if (isAddressPresent.get().getUserId() == userId) {
					return isAddressPresent.get();
				}
				throw new OrderDetailsNotFoundException(500, "Invalid User");
			}
			throw new OrderDetailsNotFoundException(500, "Address Not Found");
		}
		throw new OrderDetailsNotFoundException(500, "User Not Found");
	}


}
