package com.group.inventory;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController("/hello")
public class WelcomeResource {
    @GetMapping
    public String welcome(HttpServletRequest request) {
        String welcomeStr = "Welcome to %s to the inventory_app";
        return String.format(welcomeStr, request.getRemoteAddr());
    }
}
