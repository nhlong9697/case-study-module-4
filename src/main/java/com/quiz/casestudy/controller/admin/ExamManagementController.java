package com.quiz.casestudy.controller.admin;

import com.quiz.casestudy.model.*;
import com.quiz.casestudy.service.assign.IQuizAssignmentService;
import com.quiz.casestudy.service.module.IModuleService;
import com.quiz.casestudy.service.program.IProgramService;
import com.quiz.casestudy.service.question.IQuestionService;
import com.quiz.casestudy.service.quiz.IQuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    private IModuleService moduleService;
    @Autowired
    private IProgramService programService;
    @Autowired
    private IQuizAssignmentService quizAssignmentService;

    @ModelAttribute("programs")
    public Iterable<Program> programs(){
        return programService.findAll();
    }

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
        modelAndView.addObject("newQuiz", new Quiz());
        return modelAndView;
    }
    @PostMapping("/getModuleByProgram")
    public ResponseEntity<Iterable<Module>> getClassesByProgram(@RequestBody Program program) {
        return new ResponseEntity<>(moduleService.findAllByProgram(program), HttpStatus.OK);
    }
    @PostMapping("/getQuestionCountByModuleAndType/{moduleId}/{type}")
    public ResponseEntity<Long> getQuestioncountByModuleAndType(@PathVariable Long moduleId,
                                                             @PathVariable int type) {
        if (!moduleService.findById(moduleId).isPresent()) {
            return null;
        } else {
            Module module = moduleService.findById(moduleId).get();
            return new ResponseEntity<>(questionService.countAllByTypeAndModule(type,module), HttpStatus.OK);
        }
    }
    @PostMapping("/quiz/create")
    public ModelAndView classesCreate(@ModelAttribute("newClass") @Validated Quiz quiz,
                                      BindingResult bindingResult,
                                      @RequestParam(value = "easyQuestionCount") int easyQuestionCount,
                                      @RequestParam(value = "mediumQuestionCount") int mediumQuestionCount,
                                      @RequestParam(value = "hardQuestionCount") int hardQuestionCount,
                                      @RequestParam(value = "moduleId") Long moduleId) {
        ModelAndView modelAndView = new ModelAndView("quizmanagement/quiz/quizCreate");
        if (bindingResult.hasFieldErrors()) {
            return modelAndView;
        }
        if (moduleService.findById(moduleId).isPresent()) {
            Module module = moduleService.findById(moduleId).get();
            Set<Question> easyQuestion = questionService.getRandomQuestionSetByTypeAndModule(1,
                    module,
                    easyQuestionCount);
            Set<Question> mediumQuestion = questionService.getRandomQuestionSetByTypeAndModule(2,
                    module,mediumQuestionCount);
            Set<Question> hardQuestion = questionService.getRandomQuestionSetByTypeAndModule(3,
                    module,
                    hardQuestionCount);
            Set<Question> questionSet = new HashSet<>();
            questionSet.addAll(easyQuestion);
            questionSet.addAll(mediumQuestion);
            questionSet.addAll(hardQuestion);
            quiz.setQuestions(questionSet);
            quizService.save(quiz);
            modelAndView.addObject("newQuiz", new Quiz());
            modelAndView.addObject("success", "New quiz is created");
        } else {
            return new ModelAndView("error.404");
        }

        return modelAndView;
    }
    @GetMapping("/quiz/view/{quizId}")
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
    @GetMapping("/assign")
    public ModelAndView assignList() {
        ModelAndView modelAndView = new ModelAndView("quizmanagement/assign/assignList");
        Iterable<QuizAssignment> assignList = quizAssignmentService.findAll();
        modelAndView.addObject("assignList", assignList);
        return modelAndView;
    }

    @GetMapping("/assign/create")
    public ModelAndView assignCreateForm() {
        ModelAndView modelAndView = new ModelAndView("quimanagement/assign/assignCreate");
        modelAndView.addObject("newQuizAssignment", new QuizAssignment());
        return modelAndView;
    }

//    @PostMapping("/assign/create")
//    public ModelAndView createQuizAssignment() {
//        ModelAndView
//    }
}
