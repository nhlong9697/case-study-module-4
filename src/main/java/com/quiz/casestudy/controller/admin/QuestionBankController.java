package com.quiz.casestudy.controller.admin;

import com.quiz.casestudy.model.Answer;
import com.quiz.casestudy.model.Module;
import com.quiz.casestudy.model.Program;
import com.quiz.casestudy.model.Question;
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

    @ModelAttribute("programs")
    public Iterable<Program> programs(){
        return programService.findAll();
    }

    @ModelAttribute("modules")
    public Iterable<Module> modules(){
        return moduleService.findAll();
    }

    @ModelAttribute("questions")
    public Iterable<Question> questions(){
        return questionService.findAll();
    }

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

    @PostMapping("/questionbank/program/create")
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
    @GetMapping("/questionbank/program/edit/{id}")
    public ModelAndView programEditForm(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("program/programEdit");
        modelAndView.addObject("programEdit",programService.findById(id));
        return modelAndView;
    }

    @PostMapping("/questionbank/program/edit")
    public ModelAndView programEdit(@ModelAttribute("programEdit") Program program) {
        MultipartFile file = program.getImage();
        String fileName = file.getOriginalFilename();
        program.setAvatar(fileName);
        programService.save(program);
        ModelAndView modelAndView = new ModelAndView("program/programEdit");
        modelAndView.addObject("programEdit", program);
        modelAndView.addObject("message", "Program updated successfully");
        return modelAndView;
    }

    @GetMapping("/questionbank/program/delete/{id}")
    public String programDeleteForm(@PathVariable Long id) {
        programService.remove(id);
        return "redirect:/admin/questionbank/program";
    }

    @GetMapping("/questionbank/program/{id}/module")
    public ModelAndView moduleList(@PathVariable("id") Long id){
        Program program = programService.findById(id).get();
        if (program == null){
            return new ModelAndView("/error.404");
        }
        Iterable<Module> modules = moduleService.findAllByProgram(program);
        ModelAndView modelAndView = new ModelAndView("module/moduleList");
        modelAndView.addObject("moduleList",modules);
        modelAndView.addObject("program",program);
        return modelAndView;
    }

    @GetMapping("/questionbank/program/{programId}/module/create")
    public ModelAndView moduleCreateForm(@PathVariable Long programId){
        ModelAndView modelAndView = new ModelAndView("module/moduleCreate");
        modelAndView.addObject("moduleCreate",new Module());
        return modelAndView;
    }

    @PostMapping("/questionbank/program/{programId}/module/create")
    public ModelAndView moduleCreate(@ModelAttribute("moduleCreate") Module module, @PathVariable Long programId){
        Optional<Program> program = programService.findById(programId);
        if(program.isPresent()){
            module.setProgram(program.get());
        }
        moduleService.save(module);
        ModelAndView modelAndView = new ModelAndView("module/moduleCreate");
        modelAndView.addObject("moduleCreate",new Module());
        modelAndView.addObject("message", "Module create successfully");
        return modelAndView;
    }
    @GetMapping("/questionbank/program/{programId}/module/edit/{id}")
    public ModelAndView moduleEditForm(@PathVariable Long programId, @PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("module/moduleEdit");
        modelAndView.addObject("moduleEdit",moduleService.findById(id));
        modelAndView.addObject("programId",programId);
        return modelAndView;
    }

    @PostMapping("/questionbank/program/{programId}/module/edit")
    public ModelAndView moduleEdit(@ModelAttribute("moduleEdit") Module module,@PathVariable Long programId) {
        Optional<Program> program = programService.findById(programId);
        if(program.isPresent()){
            module.setProgram(program.get());
        }
        moduleService.save(module);
        ModelAndView modelAndView = new ModelAndView("module/moduleEdit");
        modelAndView.addObject("moduleEdit", module);
        modelAndView.addObject("message", "Module updated successfully");
        return modelAndView;
    }

    @GetMapping("/questionbank/program/module/delete/{id}")
    public String moduleDeleteForm(@PathVariable Long id) {
        moduleService.remove(id);
        return "redirect:/admin/questionbank/program";
    }

    @GetMapping("/questionbank/program/module/{id}/question")
    public ModelAndView questionList(@PathVariable("id") Long id){
        Module module = moduleService.findById(id).get();
        if (module == null){
            return new ModelAndView("/error.404");
        }
        Iterable<Question> questions = questionService.findAllByModule(module);
        ModelAndView modelAndView = new ModelAndView("question/questionList");
        modelAndView.addObject("module",module);
        modelAndView.addObject("questionList",questions);
        return modelAndView;
    }

    @GetMapping("/questionbank/program/module/{moduleId}/question/create")
    public ModelAndView questionCreateForm(@PathVariable Long moduleId){
        ModelAndView modelAndView = new ModelAndView("question/questionCreate");
        modelAndView.addObject("questionCreate",new Question());
        return modelAndView;
    }

    @PostMapping("/questionbank/program/module/{moduleId}/question/create")
    public ModelAndView questionCreate(@ModelAttribute("questionCreate") Question question, @PathVariable Long moduleId){
        Optional<Module> module = moduleService.findById(moduleId);
        if(module.isPresent()){
            question.setModule(module.get());
        }
        questionService.save(question);
        ModelAndView modelAndView = new ModelAndView("question/questionCreate");
        modelAndView.addObject("questionCreate",new Question());
        modelAndView.addObject("message", "Question create successfully");
        return modelAndView;
    }

    @GetMapping("/questionbank/program/module/{moduleId}/question/edit/{id}")
    public ModelAndView questionEditForm(@PathVariable Long moduleId,@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("question/questionEdit");
        modelAndView.addObject("questionEdit",questionService.findById(id));
        modelAndView.addObject("moduleId",moduleId);
        return modelAndView;
    }

    @PostMapping("/questionbank/program/module/{moduleId}/question/edit")
    public ModelAndView questionEdit(@ModelAttribute("questionEdit") Question question, @PathVariable Long moduleId) {
        Optional<Module> module = moduleService.findById(moduleId);
        if(module.isPresent()){
            question.setModule(module.get());
        }
        questionService.save(question);
        ModelAndView modelAndView = new ModelAndView("question/questionEdit");
        modelAndView.addObject("questionEdit", question);
        modelAndView.addObject("message", "Question updated successfully");
        return modelAndView;
    }

    @GetMapping("/questionbank/program/module/question/delete/{id}")
    public String questionDeleteForm(@PathVariable Long id) {
        questionService.remove(id);
        return "redirect:/admin/questionbank/program";
    }

    @GetMapping("/questionbank/program/module/question/{id}/answer")
    public ModelAndView answerList(@PathVariable("id") Long id){
        Question question = questionService.findById(id).get();
        if (question == null){
            return new ModelAndView("/error.404");
        }
        Iterable<Answer> answers = answerService.findAllByQuestion(question);
        ModelAndView modelAndView = new ModelAndView("answer/answerList");
        modelAndView.addObject("question",question);
        modelAndView.addObject("answers",answers);
        return modelAndView;
    }

    @GetMapping("/questionbank/program/module/question/{questionId}/answer/create")
    public ModelAndView answerCreateForm(@PathVariable Long questionId){
        ModelAndView modelAndView = new ModelAndView("answer/answerCreate");
        modelAndView.addObject("answerCreate",new Answer());
        return modelAndView;
    }

    @PostMapping("/questionbank/program/module/question/{questionId}/answer/create")
    public ModelAndView answerCreate(@ModelAttribute("answerCreate") Answer answer, @PathVariable Long questionId){
        Optional<Question> question = questionService.findById(questionId);
        if(question.isPresent()){
            answer.setQuestion(question.get());
        }
        answerService.save(answer);
        ModelAndView modelAndView = new ModelAndView("answer/answerCreate");
        modelAndView.addObject("answerCreate",new Answer());
        modelAndView.addObject("message", "answer create successfully");
        return modelAndView;
    }

    @GetMapping("/questionbank/program/module/question/{questionId}/answer/edit/{id}")
    public ModelAndView answerEditForm(@PathVariable Long questionId,@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("answer/answerEdit");
        modelAndView.addObject("answerEdit",answerService.findById(id));
        modelAndView.addObject("questionId",questionId);
        return modelAndView;
    }

    @PostMapping("/questionbank/program/module/question/{questionId}/answer/edit")
    public ModelAndView answerEdit(@ModelAttribute("answerEdit") Answer answer, @PathVariable Long questionId) {
        Optional<Question> question = questionService.findById(questionId);
        if(question.isPresent()){
            answer.setQuestion(question.get());
        }
        answerService.save(answer);
        ModelAndView modelAndView = new ModelAndView("answer/answerEdit");
        modelAndView.addObject("answerEdit", answer);
        modelAndView.addObject("message", "answer updated successfully");
        return modelAndView;
    }
}
