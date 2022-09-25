package com.bridgelabz.bookstoreorderservice.controller;

import com.bridgelabz.bookstoreorderservice.dto.AddressDTO;
import com.bridgelabz.bookstoreorderservice.model.AddressModel;
import com.bridgelabz.bookstoreorderservice.service.IAddressService;
import com.bridgelabz.bookstoreorderservice.util.OrderResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("address")
public class AddressController {
	@Autowired
	IAddressService addressService;

	@PostMapping("/insertaddress")
	public ResponseEntity<OrderResponse> inserAddress(@RequestBody AddressDTO addressDTO, @RequestHeader String token) {
		AddressModel addressModel = addressService.inserAddress(addressDTO,token);
		OrderResponse orderResponse = new OrderResponse(200, "Successfull", addressModel);
		return new ResponseEntity<OrderResponse>(orderResponse,HttpStatus.OK);
	}

	@PutMapping("/updateaddress/{addressId}")
	public ResponseEntity<OrderResponse> updateAddress(@PathVariable Long addressId, @RequestBody AddressDTO addressDTO, @RequestHeader String token) {
		AddressModel addressModel = addressService.updateAddress(addressId,addressDTO,token);
		OrderResponse orderResponse = new OrderResponse(200, "Successfull", addressModel);
		return new ResponseEntity<OrderResponse>(orderResponse,HttpStatus.OK);
	}
	
	@GetMapping("/fetchalladdress")
	public ResponseEntity<OrderResponse> fetchAllAddresses(@RequestHeader String token) {
		List<AddressModel> addressModel = addressService.fetchAllAddresses(token);
		OrderResponse orderResponse = new OrderResponse(200, "Successfull", addressModel);
		return new ResponseEntity<OrderResponse>(orderResponse,HttpStatus.OK);
	}
	
	@GetMapping("/getaddress/{addressId}")
	public ResponseEntity<OrderResponse> getAddressById(@PathVariable Long addressId,@RequestHeader String token) {
		AddressModel addressModel = addressService.getAddressById(addressId,token);
		OrderResponse orderResponse = new OrderResponse(200, "Successfull", addressModel);
		return new ResponseEntity<OrderResponse>(orderResponse,HttpStatus.OK);
	}
	
	@DeleteMapping("/deleteAddress/{addressId}")
	public ResponseEntity<OrderResponse> deleteAddressById(@PathVariable Long addressId,@RequestHeader String token) {
		AddressModel addressModel = addressService.deleteAddressById(addressId,token);
		OrderResponse orderResponse = new OrderResponse(200, "Successfull", addressModel);
		return new ResponseEntity<OrderResponse>(orderResponse,HttpStatus.OK);
	}
}
