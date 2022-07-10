package com.banking.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class TransferInfoController {

    @GetMapping("/transferInfo")
    public ModelAndView showTransferInfoPage() {
        return new ModelAndView("/transfer-history/transferInfo");
    }
}
