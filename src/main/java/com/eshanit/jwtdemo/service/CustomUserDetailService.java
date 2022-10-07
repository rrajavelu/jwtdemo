package com.eshanit.jwtdemo.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CustomUserDetailService implements UserDetailsService {

    // this method actually does the validation for user existence
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if(username.equals("rajavelu")){ // here you can make a DB Call with help of Repository and do the validation
            return new User("rajavelu", "secret", new ArrayList<>()); // Assume these are return from Database upon success
        } else {
            throw new UsernameNotFoundException("user does not exist!");
        }
    }
}
