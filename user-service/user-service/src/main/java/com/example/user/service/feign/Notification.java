package com.example.user.service.feign;


import com.example.user.service.dto.Email;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name="Notification-service",url="http://localhost:8087")
public interface Notification {

    @PostMapping("/sendmail")
     String message(@RequestBody Email email);
}
