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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;

@Controller
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
    @GetMapping("/program/{programId}")
    public ModelAndView moduleEditForm(@PathVariable Long programId) {
        ModelAndView modelAndView = new ModelAndView("program/programEdit");
        modelAndView.addObject("programEdit",programService.findById(programId));
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

    @GetMapping("/program/delete/{programId}")
    public String deleteForm(@PathVariable Long programId) {
        programService.remove(programId);
        return "redirect:/program";
    }

    @GetMapping("/program/{programId}/module")
    public ModelAndView moduleList(@PathVariable("programId") Long programId){
        Program program = programService.findById(programId).get();
        if (program == null){
            return new ModelAndView("/error.404");
        }
        Iterable<Module> modules = moduleService.findAllByProgram(program);
        ModelAndView modelAndView = new ModelAndView("module/moduleList");
        modelAndView.addObject("moduleList",modules);
        modelAndView.addObject("program",program);
        return modelAndView;
    }

    @GetMapping("/program/module/create")
    public ModelAndView moduleCreateForm(){
        ModelAndView modelAndView = new ModelAndView("module/moduleCreate");
        modelAndView.addObject("moduleCreate",new Module());
        return modelAndView;
    }

    @PostMapping("/program/module/create")
    public ModelAndView moduleCreate(@ModelAttribute Module module){
        ModelAndView modelAndView = new ModelAndView("module/moduleCreate");
        modelAndView.addObject("moduleCreate",new Program());
        return modelAndView;
    }
}
