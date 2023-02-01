package com.example.sampleapi.controllers;

import com.example.sampleapi.models.Student;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/home")
public class HomeController {
    @GetMapping
    public String home(){
        return "Hello Home";
    }
}
