package com.task.service;

import com.task.configuration.JwtGenerate;
import com.task.configuration.JwtResponse;
import com.task.entity.User;
import com.task.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthorizeService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MyUserDetailsService myUserDetailsService;

    public ResponseEntity<JwtResponse> signup(User user) throws Exception
    {
        String email=user.getEmail();
        String password=user.getPassword();
        String fullname=user.getFullname();
        String role=user.getRole();

        User isEmailExist=userRepository.findByEmail(email);
        if(isEmailExist!=null)
        {
            throw new Exception("Email is already used with another Account");
        }

        User createUser=new User();
        createUser.setEmail(email);
        createUser.setFullname(fullname);
        createUser.setRole(role);
        createUser.setPassword(passwordEncoder.encode(password));

        User saveUser=userRepository.save(createUser);

        Authentication authentication= new UsernamePasswordAuthenticationToken(email,password);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token=JwtGenerate.generateToken(authentication);
        JwtResponse jwtResponse=new JwtResponse();
        jwtResponse.setJwt(token);
        jwtResponse.setMessage("Register is successful");
        jwtResponse.setStatus(true);
        return new ResponseEntity<>(jwtResponse, HttpStatus.OK);
    }

    public ResponseEntity<JwtResponse> signin(JwtResponse.LoginDetails loginDetails)
    {
        String username=loginDetails.getEmail();
        String password=loginDetails.getPassword();
        Authentication authentication=authenticate(username,password);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token=JwtGenerate.generateToken(authentication);
        JwtResponse jwtResponse=new JwtResponse();
        jwtResponse.setMessage("login successful");
        jwtResponse.setJwt(token);
        jwtResponse.setStatus(true);
        return new ResponseEntity<>(jwtResponse,HttpStatus.OK);
    }

    private Authentication authenticate(String username,String password)
    {
        UserDetails userDetails=myUserDetailsService.loadUserByUsername(username);
        if(userDetails==null)
        {
            throw new BadCredentialsException("Invalid username or password");
        }
        if(!passwordEncoder.matches(password,userDetails.getPassword()))
        {
            throw new BadCredentialsException("Invalid username or password");
        }
        return new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
    }
}
