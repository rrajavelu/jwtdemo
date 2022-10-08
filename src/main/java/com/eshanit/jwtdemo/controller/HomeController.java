package com.eshanit.jwtdemo.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    //@PreAuthorize("hasRole('ADMIN')")
    // @PreAuthorize("hasAnyRole('ADMIN','USER')")
    //@PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_USER')")
    @GetMapping("/hello")
    public String sayHello(){
        return "Hello from HomeController";
    }
}
