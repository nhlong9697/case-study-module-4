package com.quiz.casestudy.controller.admin;

import com.quiz.casestudy.model.Classes;
import com.quiz.casestudy.model.Question;
import com.quiz.casestudy.model.Quiz;
import com.quiz.casestudy.service.answer.IAnswerService;
import com.quiz.casestudy.service.question.IQuestionService;
import com.quiz.casestudy.service.quiz.IQuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashSet;
import java.util.Set;

@Controller
@RequestMapping("/admin/quizmanagement")
public class ExamManagementController {
    @Autowired
    private IQuizService quizService;
    @Autowired
    private IQuestionService questionService;
    @Autowired
    private IAnswerService answerService;

    @GetMapping("/quiz")
    public ModelAndView quizList() {
        ModelAndView modelAndView = new ModelAndView("quizmanagement/quiz/quizList");
        Iterable<Quiz> quizList = quizService.findAll();
        modelAndView.addObject("quizList", quizList);
        return modelAndView;
    }

    @GetMapping("/quiz/create")
    public ModelAndView createQuizForm() {
        ModelAndView modelAndView = new ModelAndView("quizmanagement/quiz/quizCreate");

        modelAndView.addObject("easyQuestionCount", questionService.countAllByType(1));
        modelAndView.addObject("mediumQuestionCount", questionService.countAllByType(2));
        modelAndView.addObject("hardQuestionCount", questionService.countAllByType(3));
        modelAndView.addObject("newQuiz", new Quiz());
        return modelAndView;
    }

    @PostMapping("/quiz/create")
    public ModelAndView classesCreate(@ModelAttribute("newClass") @Validated Quiz quiz,
                                      BindingResult bindingResult,
                                      @RequestParam(value = "easyQuestionCount") int easyQuestionCount,
                                      @RequestParam(value = "mediumQuestionCount") int mediumQuestionCount,
                                      @RequestParam(value = "hardQuestionCount") int hardQuestionCount) {
        ModelAndView modelAndView = new ModelAndView("quizmanagement/quiz/quizCreate");
        if (bindingResult.hasFieldErrors()) {
            return modelAndView;
        }
        Set<Question> easyQuestion = questionService.getRandomQuestionSetByType(1,easyQuestionCount);
        Set<Question> mediumQuestion = questionService.getRandomQuestionSetByType(2,mediumQuestionCount);
        Set<Question> hardQuestion = questionService.getRandomQuestionSetByType(3,hardQuestionCount);
        Set<Question> questionSet = new HashSet<>();
        questionSet.addAll(easyQuestion);
        questionSet.addAll(mediumQuestion);
        questionSet.addAll(hardQuestion);
        quiz.setQuestions(questionSet);
        quizService.save(quiz);
        modelAndView.addObject("classes", new Classes());
        modelAndView.addObject("success", "New class created");
        return modelAndView;
    }
    @GetMapping("quiz/view/{quizId}")
    public ModelAndView viewQuiz(@PathVariable Long quizId) {
        ModelAndView modelAndView = new ModelAndView("quizmanagement/quiz/quizView");
        if (quizService.findById(quizId).isPresent()) {
            Quiz quiz = quizService.findById(quizId).get();
            Set<Question> questionSet = quiz.getQuestions();
            modelAndView.addObject("questionSet", questionSet);
        } else {
            return new ModelAndView("error.404");
        }

        return modelAndView;
    }

}
