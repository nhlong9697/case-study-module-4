package com.quiz.casestudy.controller.user;

import com.quiz.casestudy.model.AppUser;
import com.quiz.casestudy.model.Student;
import com.quiz.casestudy.service.student.IStudentService;
import com.quiz.casestudy.service.studentquiz.IStudentQuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/home/quiz")
@SessionAttributes("student")
public class StudentQuizController {
    @Autowired
    private IStudentQuizService studentQuizService;

    @Autowired
    private IStudentService studentService;

    @GetMapping
    public ModelAndView testList(@ModelAttribute("student") Student student) {
        ModelAndView modelAndView = new ModelAndView("studentquiz/studentQuizList");
        return modelAndView;
    }
}
