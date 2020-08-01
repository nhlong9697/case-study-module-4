package com.quiz.casestudy.controller.admin;

import com.quiz.casestudy.model.Classes;
import com.quiz.casestudy.model.Module;
import com.quiz.casestudy.model.Program;
import com.quiz.casestudy.model.Question;
import com.quiz.casestudy.service.answer.IAnswerService;
import com.quiz.casestudy.service.classes.IClassesService;
import com.quiz.casestudy.service.module.IModuleService;
import com.quiz.casestudy.service.program.IProgramService;
import com.quiz.casestudy.service.question.IQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/admin/classmanagement")
public class ClassManagementController {
    @Autowired
    private IClassesService classesService;
    @Autowired
    private IProgramService programService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private Environment environment;

    @ModelAttribute("programs")
    public Iterable<Program> programs(){
        return programService.findAll();
    }

    @GetMapping
    public ModelAndView classList() {
        ModelAndView modelAndView = new ModelAndView("classmanagement/class/list");
        Iterable<Classes> classList = classesService.findAll();
        modelAndView.addObject("classList", classList);
        return modelAndView;
    }
    @GetMapping("/class/create")
    public ModelAndView createClassForm() {
        ModelAndView modelAndView = new ModelAndView("classmanagement/class/list");
        modelAndView.addObject("newClass", new Classes());
        return modelAndView;
    }

}
