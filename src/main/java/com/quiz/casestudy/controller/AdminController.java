package com.quiz.casestudy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @GetMapping
    public ModelAndView showAdmin() {
        ModelAndView modelAndView = new ModelAndView("admin");
        return new ModelAndView("admin");
    }
}
