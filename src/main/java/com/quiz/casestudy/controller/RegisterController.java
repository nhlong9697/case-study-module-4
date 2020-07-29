package com.quiz.casestudy.controller;

import com.quiz.casestudy.model.AppRole;
import com.quiz.casestudy.model.AppUser;
import com.quiz.casestudy.model.Student;
import com.quiz.casestudy.service.student.IStudentService;
import com.quiz.casestudy.service.userservice.IAppRoleService;
import com.quiz.casestudy.service.userservice.IAppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/register")
public class RegisterController {
    @Autowired
    private IAppUserService userService;
    @Autowired
    private IAppRoleService roleService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @GetMapping
    public ModelAndView registerForm() {
        ModelAndView modelAndView = new ModelAndView("register");
        modelAndView.addObject("user", new AppUser());
        return modelAndView;
    }

    @PostMapping
    public ModelAndView createUser(@ModelAttribute("user") AppUser appUser) {
       ModelAndView modelAndView = new ModelAndView("register");
       appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));
       AppRole adminRole = roleService.findById(1L).get();
       appUser.setRole(adminRole);
       userService.save(appUser);
       modelAndView.addObject("message","register success");
       return modelAndView;
    }
}
