package com.paymentgateway.controller;

import com.paymentgateway.dto.BookingRequest;
import com.paymentgateway.dto.StripeResponse;
import com.paymentgateway.service.StripeServcie;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    private StripeServcie stripeServcie;

    public PaymentController(StripeServcie stripeServcie) {
        this.stripeServcie = stripeServcie;
    }
      @PostMapping("/checkout")
    public ResponseEntity<StripeResponse> checkoutProduct(@RequestBody BookingRequest bookingRequest){
        StripeResponse stripeResponse = stripeServcie.checkoutProducts(bookingRequest);

        return new ResponseEntity<>(stripeResponse,HttpStatus.OK);

    }
}
