package com.submission.service;

import com.submission.entity.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "USER-SERVICE",url = "http://localhost:8081")
public interface UserService {

    @GetMapping("/tms/users/getUser")
    public ResponseEntity<User> getUser(@RequestHeader("Authorization") String jwt);
}
