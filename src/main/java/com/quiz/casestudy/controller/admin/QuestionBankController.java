package com.quiz.casestudy.controller.admin;

import com.quiz.casestudy.model.Program;
import com.quiz.casestudy.service.program.IProgramService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
    private Environment environment;

    @GetMapping("/questionbank/program")
    public ModelAndView programList(){
        ModelAndView modelAndView = new ModelAndView("program/programList");
        modelAndView.addObject("programList",programService.findAll());
        return modelAndView;
    }

    @GetMapping("/questionbank/program/create")
    public ModelAndView programCreateForm(){
        ModelAndView modelAndView = new ModelAndView("program/programCreate");
        modelAndView.addObject("programCreate",new Program());
        return modelAndView;
    }

    @PostMapping("/admin/questionbank/program/create")
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
}
