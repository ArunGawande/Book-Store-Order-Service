package com.bridgelabz.bookstoreorderservice.exception.exceptionhandler;

import com.bridgelabz.bookstoreorderservice.exception.OrderDetailsNotFoundException;
import com.bridgelabz.bookstoreorderservice.util.OrderResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class OrderDetailsExceptionHandler {
    @ExceptionHandler(OrderDetailsNotFoundException.class)
    public ResponseEntity<OrderResponse> response(OrderDetailsNotFoundException exception) {
        OrderResponse orderResponse = new OrderResponse();
        orderResponse.setStatusCode(400);
        orderResponse.setMessage(exception.getMessage());
        return new ResponseEntity<OrderResponse>(orderResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<OrderResponse>
    defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {
    	OrderResponse orderResponse = new OrderResponse();
    	orderResponse.setStatusCode(500);
    	orderResponse.setMessage(e.getMessage());
        return new ResponseEntity<OrderResponse>(orderResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
