package com.bridgelabz.bookstoreorderservice.service;

import com.bridgelabz.bookstoreorderservice.exception.OrderDetailsNotFoundException;
import com.bridgelabz.bookstoreorderservice.model.AddressModel;
import com.bridgelabz.bookstoreorderservice.model.OrderBookModel;
import com.bridgelabz.bookstoreorderservice.repository.AddressRepository;
import com.bridgelabz.bookstoreorderservice.repository.OrderServiceRepository;
import com.bridgelabz.bookstoreorderservice.util.BookResponse;
import com.bridgelabz.bookstoreorderservice.util.CartResponse;
import com.bridgelabz.bookstoreorderservice.util.TokenUtil;
import com.bridgelabz.bookstoreorderservice.util.UserResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class OrderService implements IOrderService{
	@Autowired
	OrderServiceRepository orderServiceRepository;
	@Autowired
	TokenUtil tokenUtil;
	@Autowired
	RestTemplate restTemplate;
	@Autowired
	AddressRepository addressRepository;
	@Autowired
	MailService mailService;

	@Override
	public OrderBookModel placeOrder(Long cartId, Long addressId, String token) {
		Long userId = tokenUtil.decodeToken(token);
		UserResponse isUserPresent = restTemplate.getForObject("http://BOOKSTORE-USERSERVICE:8081/bookstoreuser/validateuser/" + userId, UserResponse.class);
		if (isUserPresent.getErrorCode() == 200) {
			CartResponse isCartPresent = restTemplate.getForObject("http://BOOKSTORE-CARTSERVICE:8083/cartservice/getcart/" + cartId, CartResponse.class);
			if (isUserPresent.getErrorCode() == 200) {
				if (isUserPresent.getObject().getUserId() == isCartPresent.getObject().getUserId()) {
					Optional<AddressModel> isAddressPresent = addressRepository.findById(addressId);
					OrderBookModel order = new OrderBookModel();
					order.setOrderDate(LocalDateTime.now());
					order.setBookId(isCartPresent.getObject().getBookId());
					order.setPrice(isCartPresent.getObject().getTotalPrice());
					order.setQuantity(isCartPresent.getObject().getQuantity());
					order.setUserId(isCartPresent.getObject().getUserId());
					order.setCartId(cartId);
					order.setCancel(false);
					if (isAddressPresent.isPresent()) {
						if (isAddressPresent.get().getUserId() == isUserPresent.getObject().getUserId()) {
							order.setAddress(isAddressPresent.get());
						}
						else {
							throw new OrderDetailsNotFoundException(500, "Address UserId and User Id Not Match");
						}
					}
					else {
						throw new OrderDetailsNotFoundException(500, "Address Not Found This User Id");
					}
					orderServiceRepository.save(order);
					String body = "Your Order Placer with Order Id is :" +order.getOrderId();
					String subject = "Order Successfully Placed";
					mailService.send(isUserPresent.getObject().getEmailId(), subject, body);
					CartResponse isPresent = restTemplate.getForObject("http://BOOKSTORE-CARTSERVICE:8083/cartservice/delete/" + order.getCartId(), CartResponse.class);
					return order;
				}
				throw new OrderDetailsNotFoundException(500, "No Cart Found For This User Id");			
			}
			throw new OrderDetailsNotFoundException(500, "No Cart Found For This User Id");
		}
		return null;
	}

	@Override
	public OrderBookModel cancelOrder(Long orderId, String token) {
		Long userId = tokenUtil.decodeToken(token);
		UserResponse isUserPresent = restTemplate.getForObject("http://BOOKSTORE-USERSERVICE:8081/bookstoreuser/validateuser/" + userId, UserResponse.class);
		if (isUserPresent.getErrorCode() == 200) {
			Optional<OrderBookModel> isOrderPresent = orderServiceRepository.findById(orderId);
			if (isOrderPresent.isPresent()) {
				isOrderPresent.get().setCancel(true);
				BookResponse isBookIdPresent = restTemplate.getForObject("http://BOOKSTORE-BOOKSERVICE:8082/bookdetails/updatequantity/" +
						isOrderPresent.get().getBookId() +"/"+ isOrderPresent.get().getQuantity(), BookResponse.class);
				orderServiceRepository.delete(isOrderPresent.get());
				return isOrderPresent.get();
			}
			throw new OrderDetailsNotFoundException(500, "No Order Found For This Order Id");
		}
		throw new OrderDetailsNotFoundException(500, "No Order Found For This User Id");
	}
	@Override
	public List<OrderBookModel> getUserOrders(String token) {
		Long userId = tokenUtil.decodeToken(token);
		UserResponse isUserPresent = restTemplate.getForObject("http://BOOKSTORE-USERSERVICE:8081/bookstoreuser/validateuser/" + userId, UserResponse.class);
		if (isUserPresent.getErrorCode() == 200) {
			List<OrderBookModel> isOrdersPresent = orderServiceRepository.findByUserId(userId);
			if (isOrdersPresent.size()>0) {
				return isOrdersPresent;
			}
		}
		throw new OrderDetailsNotFoundException(500, "Invalid User");
	}
	@Override
	public List<OrderBookModel> getAllOrders(String token) {
		Long userId = tokenUtil.decodeToken(token);
		UserResponse isUserPresent = restTemplate.getForObject("http://BOOKSTORE-USERSERVICE:8081/bookstoreuser/validateuser/" + userId, UserResponse.class);
		if (isUserPresent.getErrorCode() == 200) {
			List<OrderBookModel> isOrdersPresent = orderServiceRepository.findAll();
			if (isOrdersPresent.size()>0) {
				return isOrdersPresent;
			}
			throw new OrderDetailsNotFoundException(500, "No Orders Found");
		}
		throw new OrderDetailsNotFoundException(500, "Invalid User");
	}



}
