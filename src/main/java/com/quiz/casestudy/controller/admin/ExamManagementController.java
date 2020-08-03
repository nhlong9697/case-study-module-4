package com.quiz.casestudy.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/admin/quizmanagement")
public class ExamManagementController {
    @GetMapping("/quiz")
    public ModelAndView quizList() {
        ModelAndView modelAndView = new ModelAndView("quizmanagement/quiz/quizList");
        return modelAndView;
    }
}
