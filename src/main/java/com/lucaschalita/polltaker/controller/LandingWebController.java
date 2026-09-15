package com.lucaschalita.polltaker.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LandingWebController {

    @GetMapping("/")
    public String landingPage() {
        return "index";
    }
}