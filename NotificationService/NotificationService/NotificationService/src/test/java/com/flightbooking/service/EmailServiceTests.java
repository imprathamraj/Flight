package com.flightbooking.service;



import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EmailServiceTests {

   @Autowired
    private EmailService emailService;

   @Test
    void testSendMail(){
       emailService.sendEmail("yogikushwah777@gmail.com","testing the message","Hii app kese h");
   }

}