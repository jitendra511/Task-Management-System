package com.task.service;

import com.task.configuration.JwtGenerate;
import com.task.entity.User;
import com.task.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User getUser(String jwt)
    {
        String email= JwtGenerate.getEmailFromJwtToken(jwt);
        return userRepository.findByEmail(email);

    }
}
