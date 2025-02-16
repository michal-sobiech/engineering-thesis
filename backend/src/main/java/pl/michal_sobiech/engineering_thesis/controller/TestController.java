package pl.michal_sobiech.engineering_thesis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import pl.michal_sobiech.engineering_thesis.service.CustomerService;


@RestController
public class TestController {

    @Autowired
    CustomerService customerService;

    @GetMapping("/")
    public String testEndpoint() {
        return "Sample text!";
    }
}