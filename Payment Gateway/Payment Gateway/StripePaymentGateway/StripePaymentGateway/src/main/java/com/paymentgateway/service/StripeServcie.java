package com.paymentgateway.service;

import com.paymentgateway.dto.BookingRequest;
import com.paymentgateway.dto.StripeResponse;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.stereotype.Service;

@Service
public class StripeServcie {

//    @Value("${stripe.secretKey}")
    private String secretKey="sk_test_51R9KMz4R2tLdSFuDabfKB7MyNVaxlRftMgdy7I87UvF5D0IYOJoUaxezMAEPc4KuXCwUi3COdvqXi4nNWU2OILHd00E11fUuIP";
    //stripe->Api
    //->customer,amount ,quantity,currency
    //return session Id and Url

   public StripeResponse checkoutProducts(BookingRequest bookingRequest){
       Stripe.apiKey= secretKey;

       SessionCreateParams.LineItem.PriceData.ProductData productData = SessionCreateParams.LineItem.PriceData.ProductData.builder()
               .setName(bookingRequest.getName()).build();

       SessionCreateParams.LineItem.PriceData priceData = SessionCreateParams.LineItem.PriceData.builder().setCurrency(bookingRequest.getCurrency() == null ? "USD" : bookingRequest.getCurrency())
               .setUnitAmount(bookingRequest.getAmount())
               .setProductData(productData).build();

       SessionCreateParams.LineItem lineItem = SessionCreateParams.LineItem.builder()
               .setQuantity(bookingRequest.getQuantity())
               .setPriceData(priceData).build();

       SessionCreateParams params = SessionCreateParams.builder()
               .setMode(SessionCreateParams.Mode.PAYMENT)
               .setSuccessUrl("http://localhost:8080/success")
               .setCancelUrl("http://localhost:8080/cancel")
               .addLineItem(lineItem).build();

       Session session=null;

       try{
         session=Session.create(params);
       } catch (StripeException e) {
           System.out.println(e.getMessage());
       }


// Check if session is null before accessing it
       if (session == null) {
           return com.paymentgateway.dto.StripeResponse.builder()
                   .status("FAILURE")
                   .message("Failed to create payment session")
                   .build();
       }

       return com.paymentgateway.dto.StripeResponse.builder()
               .status("SUCCESS")
               .message("Payment session Created")
               .sessionId(session.getId())
               .sessionUrl(session.getUrl())
               .build();
   }

}
