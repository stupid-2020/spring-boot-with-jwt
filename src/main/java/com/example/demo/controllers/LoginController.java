/*
 * SCRIPT: LoginController.java
 * AUTHOR: Wu (https://github.com/stupid-2020)
 * DATE  : 17-JAN-2022
 * NOTE  : 
 */
package com.example.demo.controllers;

import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class LoginController {
    @GetMapping({"/login", "/login/"})
    public ModelAndView login(ModelMap map) {
        return new ModelAndView("forward:/login/index.html", map);
    }    
}
