package com.quiz.casestudy.controller.admin;

import com.quiz.casestudy.model.Module;
import com.quiz.casestudy.model.Program;
import com.quiz.casestudy.service.answer.IAnswerService;
import com.quiz.casestudy.service.module.IModuleService;
import com.quiz.casestudy.service.program.IProgramService;
import com.quiz.casestudy.service.question.IQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class QuestionBankController {
    @Autowired
    private IProgramService programService;

    @Autowired
    private IModuleService moduleService;

    @Autowired
    private IQuestionService questionService;

    @Autowired
    private IAnswerService answerService;

    @Autowired
    private Environment environment;

    @GetMapping("/program")
    public ModelAndView programList(){
        ModelAndView modelAndView = new ModelAndView("program/programList");
        modelAndView.addObject("programList",programService.findAll());
        return modelAndView;
    }

    @GetMapping("/program/create")
    public ModelAndView programCreateForm(){
        ModelAndView modelAndView = new ModelAndView("program/programCreate");
        modelAndView.addObject("programCreate",new Program());
        return modelAndView;
    }

    @PostMapping("/program/create")
    public ModelAndView programCreate(@ModelAttribute Program program){
        MultipartFile file = program.getImage();
        String fileName = file.getOriginalFilename();
        String fileUpload = environment.getProperty("upload.path").toString();
        try {
            FileCopyUtils.copy(file.getBytes(), new File(fileUpload + fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Program program1 = new Program(program.getName(), fileName);
        programService.save(program1);
        ModelAndView modelAndView = new ModelAndView("program/programCreate");
        modelAndView.addObject("programCreate",new Program());
        return modelAndView;
    }
    @GetMapping("/program/{id}")
    public ModelAndView moduleEditForm(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("program/programEdit");
        modelAndView.addObject("programEdit",programService.findById(id));
        return modelAndView;
    }

    @PostMapping("/program")
    public ModelAndView editStudent(@ModelAttribute("programEdit") Program program) {
        MultipartFile file = program.getImage();
        String fileName = file.getOriginalFilename();
        program.setAvatar(fileName);
        programService.save(program);
        ModelAndView modelAndView = new ModelAndView("program/programEdit");
        modelAndView.addObject("programEdit", program);
        modelAndView.addObject("message", "Program updated successfully");
        return modelAndView;
    }

    @GetMapping("/program/delete/{id}")
    public String deleteForm(@PathVariable Long id) {
        programService.remove(id);
        return "redirect:/program";
    }

    @GetMapping("/admin/questionbank/program/{id}/module")
    public ModelAndView moduleList(@PathVariable("id") Long id){
        Program program = programService.findById(id).get();
        if (program == null){
            return new ModelAndView("/error.404");
        }
        Iterable<Module> modules = moduleService.findAllByProgram(program);
        ModelAndView modelAndView = new ModelAndView("module/moduleList");
        modelAndView.addObject("moduleList",moduleService.findAll());
        modelAndView.addObject("program",program);
        return modelAndView;
    }

//    @GetMapping("/admin/questionbank/program/module/create")
//    public ModelAndView moduleCreateForm(){
//        ModelAndView modelAndView = new ModelAndView("module/moduleCreate");
//        modelAndView.addObject("moduleCreate",new Module());
//        return modelAndView;
//    }
//
//    @PostMapping("/admin/questionbank/program/create")
//    public ModelAndView moduleCreate(@ModelAttribute Module module){
//        ModelAndView modelAndView = new ModelAndView("program/programCreate");
//        modelAndView.addObject("programCreate",new Program());
//        return modelAndView;
//    }
}
