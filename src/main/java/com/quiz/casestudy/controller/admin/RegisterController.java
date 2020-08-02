package com.quiz.casestudy.controller.admin;

import com.quiz.casestudy.model.AppRole;
import com.quiz.casestudy.model.AppUser;
import com.quiz.casestudy.service.userservice.IAppRoleService;
import com.quiz.casestudy.service.userservice.IAppUserService;
import org.hibernate.jpa.internal.util.PersistenceUtilHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Locale;

@Controller
@RequestMapping("/register")
public class RegisterController {
    @Autowired
    private MessageSource messageSource;

    @Autowired
    private IAppUserService userService;

    @Autowired
    private IAppRoleService roleService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @ModelAttribute("roles")
    public Iterable<AppRole> roles() {
        return roleService.findAll();
    }

    @GetMapping
    public ModelAndView registerForm() {
        ModelAndView modelAndView = new ModelAndView("register");
        modelAndView.addObject("user", new AppUser());
        return modelAndView;
    }

    @PostMapping
    public ModelAndView createUser(@ModelAttribute("user") @Validated AppUser appUser,
                                   BindingResult bindingResult, Locale locale) {
       ModelAndView modelAndView = new ModelAndView("register");

       if (bindingResult.hasFieldErrors()) {
           return modelAndView;
       }
       appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));
       userService.save(appUser);
       modelAndView.addObject("success_message","Register success");
       return modelAndView;
    }
}
