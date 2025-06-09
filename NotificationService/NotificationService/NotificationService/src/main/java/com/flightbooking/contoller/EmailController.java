package com.flightbooking.contoller;

import com.flightbooking.entity.Email;
import com.flightbooking.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmailController {
@Autowired
    EmailService emailService;

   @PostMapping("/sendmail")
    public String message(@RequestBody Email email){
        emailService.sendEmail(email.getTo(),email.getSubject(),email.getBody());
        return "Email send successfully";
    }
}
