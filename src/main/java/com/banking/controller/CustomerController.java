package com.banking.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CustomerController {

    @GetMapping("/customers")
    public ModelAndView showListPage() {
        ModelAndView modelAndView = new ModelAndView("/customer/list");
        return modelAndView;
    }
 }
