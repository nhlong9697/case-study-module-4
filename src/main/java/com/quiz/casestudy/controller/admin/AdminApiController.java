package com.quiz.casestudy.controller.admin;

import com.quiz.casestudy.model.Classes;
import com.quiz.casestudy.model.Module;
import com.quiz.casestudy.model.Program;
import com.quiz.casestudy.model.Quiz;
import com.quiz.casestudy.service.classes.IClassesService;
import com.quiz.casestudy.service.module.IModuleService;
import com.quiz.casestudy.service.question.IQuestionService;
import com.quiz.casestudy.service.quiz.IQuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/api")
public class AdminApiController {
    @Autowired
    private IModuleService moduleService;
    @Autowired
    private IQuestionService questionService;
    @Autowired
    private IClassesService classesService;
    @Autowired
    private IQuizService quizService;
    @PostMapping("/getModuleByProgram")
    public ResponseEntity<Iterable<Module>> getModuleByProgram(@RequestBody Program program) {
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
    @PostMapping("/getClassesByProgram")
    public ResponseEntity<Iterable<Classes>> getClassesByProgram(@RequestBody Program program) {
        return new ResponseEntity<>(classesService.findByProgram(program), HttpStatus.OK);
    }
    @PostMapping("/getQuizByModule")
    public ResponseEntity<Iterable<Quiz>> getQuizByModule(@RequestBody Module module) {
        return new ResponseEntity<>(quizService.findAllByModule(module), HttpStatus.OK);
    }
}
