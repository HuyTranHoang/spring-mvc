package com.huy.crm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/")
    public String redirectToCustomerList() {
        return "redirect:/customer/list";
    }
}
