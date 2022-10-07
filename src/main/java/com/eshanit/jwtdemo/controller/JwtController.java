package com.eshanit.jwtdemo.controller;

import com.eshanit.jwtdemo.model.JwtRequest;
import com.eshanit.jwtdemo.model.JwtResponse;
import com.eshanit.jwtdemo.service.CustomUserDetailService;
import com.eshanit.jwtdemo.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class JwtController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetailService customUserDetailService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/generateToken")
    public ResponseEntity<JwtResponse> generateToken(@RequestBody JwtRequest jwtRequest){
        //authenticate the user
        UsernamePasswordAuthenticationToken upat = new UsernamePasswordAuthenticationToken(jwtRequest.getUserName(),jwtRequest.getPassword());
        authenticationManager.authenticate(upat);
        UserDetails userDetails = customUserDetailService.loadUserByUsername(jwtRequest.getUserName());
        String jwtToken = jwtUtil.generateToken(userDetails);
        JwtResponse jwtResponse = new JwtResponse(jwtToken);
        //return ResponseEntity.ok(jwtResponse);
        return new ResponseEntity<JwtResponse>(jwtResponse, HttpStatus.OK);

    }
}
