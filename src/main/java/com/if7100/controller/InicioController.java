package com.if7100.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class InicioController {
    

    @GetMapping("/inicio")
    public String index(Model model){
        return "redirect:/";
    }


}
