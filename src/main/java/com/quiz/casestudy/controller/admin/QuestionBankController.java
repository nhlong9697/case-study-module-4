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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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
        ModelAndView modelAndView = new ModelAndView("questionbank/program/programList");
        modelAndView.addObject("programList",programService.findAll());
        return modelAndView;
    }

    @GetMapping("/questionbank/program/create")
    public ModelAndView programCreateForm(){
        ModelAndView modelAndView = new ModelAndView("questionbank/program/programCreate");
        modelAndView.addObject("programCreate",new Program());
        return modelAndView;
    }

    @RequestMapping(value = "/questionbank/program/create", method = RequestMethod.POST)
    public ModelAndView programCreate(@ModelAttribute Program program){
        MultipartFile file = program.getImage();
        String fileName = file.getOriginalFilename();
        String fileUpload = environment.getProperty("upload.path");
        try {
            FileCopyUtils.copy(file.getBytes(), new File(fileUpload + fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Program program1 = new Program(program.getName(), fileName);
        programService.save(program1);
        ModelAndView modelAndView = new ModelAndView("questionbank/program/programCreate");
        modelAndView.addObject("programCreate",new Program());
        modelAndView.addObject("message", "Program updated successfully");
        return modelAndView;
    }
    @GetMapping("/questionbank/program/edit/{id}")
    public ModelAndView programEditForm(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("questionbank/program/programEdit");
        modelAndView.addObject("programEdit",programService.findById(id));
        return modelAndView;
    }

    @PostMapping("/questionbank/program/edit")
    public ModelAndView programEdit(@ModelAttribute("programEdit") Program program) {
        MultipartFile file = program.getImage();
        String fileName = file.getOriginalFilename();
        program.setAvatar(fileName);
        programService.save(program);
        ModelAndView modelAndView = new ModelAndView("questionbank/program/programEdit");
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
        ModelAndView modelAndView = new ModelAndView("/questionbank/module/moduleList");
        modelAndView.addObject("moduleList",modules);
        modelAndView.addObject("program",program);
        return modelAndView;
    }

    @GetMapping("/questionbank/program/{programId}/module/create")
    public ModelAndView moduleCreateForm(@PathVariable Long programId){
        Program program = programService.findById(programId).get();
        ModelAndView modelAndView = new ModelAndView("questionbank/module/moduleCreate");
        modelAndView.addObject("moduleCreate",new Module());
        modelAndView.addObject("program",program);
        return modelAndView;
    }

    @PostMapping("/questionbank/program/{programId}/module/create")
    public ModelAndView moduleCreate(@ModelAttribute("moduleCreate") Module module, @PathVariable Long programId){
        Program program = programService.findById(programId).get();
        if(program != null){
            module.setProgram(program);
        }
        moduleService.save(module);
        ModelAndView modelAndView = new ModelAndView("questionbank/module/moduleCreate");
        modelAndView.addObject("moduleCreate",new Module());
        modelAndView.addObject("program",program);
        modelAndView.addObject("message", "Module create successfully");
        return modelAndView;
    }
    @GetMapping("/questionbank/program/{programId}/module/edit/{id}")
    public ModelAndView moduleEditForm(@PathVariable Long programId, @PathVariable Long id) {
        Program program = programService.findById(programId).get();
        ModelAndView modelAndView = new ModelAndView("/questionbank/module/moduleEdit");
        modelAndView.addObject("program",program);
        modelAndView.addObject("moduleEdit",moduleService.findById(id));
        modelAndView.addObject("programId",programId);
        return modelAndView;
    }

    @PostMapping("/questionbank/program/{programId}/module/edit")
    public ModelAndView moduleEdit(@ModelAttribute("moduleEdit") Module module,@PathVariable Long programId) {
        Program program = programService.findById(programId).get();
        if(program != null){
            module.setProgram(program);
        }
        moduleService.save(module);
        ModelAndView modelAndView = new ModelAndView("/questionbank/module/moduleEdit");
        modelAndView.addObject("moduleEdit", module);
        modelAndView.addObject("program",program);
        modelAndView.addObject("message", "Module updated successfully");
        return modelAndView;
    }

    @GetMapping("/questionbank/program/{programId}/module/delete/{id}")
    public String moduleDeleteForm(@PathVariable Long programId,@PathVariable Long id) {
        moduleService.remove(id);
        return "redirect:/admin/questionbank/program/{programId}/module";
    }

    @GetMapping("/questionbank/program/{programId}/module/{id}/question")
    public ModelAndView questionList(@RequestParam("s")Optional<String>s,
            @PageableDefault(size = 6, direction = Sort.Direction.ASC, sort = "id") Pageable pageable,
            @PathVariable("programId") Long programId , @PathVariable("id") Long id){
        Page<Question> questions;
        Module module = moduleService.findById(id).get();
        Program program = programService.findById(programId).get();
        ModelAndView modelAndView = new ModelAndView("questionbank/question/questionList");
        if (module == null){
            return new ModelAndView("/error.404");
        }else{
            questions = questionService.findAllByModule(module,pageable);
        }
        if (s.isPresent()) {
            questions = questionService.findAllByNameContaining(s.get(), pageable);
            modelAndView.addObject("s", s.get());
        }
        modelAndView.addObject("module",module);
        modelAndView.addObject("questionList",questions);
        modelAndView.addObject("program",program);
        return modelAndView;
    }

    @GetMapping("/questionbank/program/{programId}/module/{moduleId}/question/create")
    public ModelAndView questionCreateForm(@PathVariable("programId") Long programId ,@PathVariable Long moduleId){
        Module module = moduleService.findById(moduleId).get();
        Program program = programService.findById(programId).get();
        ModelAndView modelAndView = new ModelAndView("questionbank/question/questionCreate");
        modelAndView.addObject("questionCreate",new Question());
        modelAndView.addObject("program",program);
        modelAndView.addObject("module",module);
        return modelAndView;
    }

    @PostMapping("/questionbank/program/{programId}/module/{moduleId}/question/create")
    public ModelAndView questionCreate(@ModelAttribute("questionCreate") Question question,@PathVariable("programId") Long programId, @PathVariable Long moduleId){
        Program program = programService.findById(programId).get();
        Module module = moduleService.findById(moduleId).get();
        if(module != null){
            question.setModule(module);
        }
        questionService.save(question);
        ModelAndView modelAndView = new ModelAndView("questionbank/question/questionCreate");
        modelAndView.addObject("questionCreate",new Question());
        modelAndView.addObject("program",program);
        modelAndView.addObject("module",module);
        modelAndView.addObject("message", "Question create successfully");
        return modelAndView;
    }

    @GetMapping("/questionbank/program/{programId}/module/{moduleId}/question/edit/{id}")
    public ModelAndView questionEditForm(@PathVariable Long moduleId,@PathVariable("programId") Long programId,@PathVariable Long id) {
        Program program = programService.findById(programId).get();
        Module module = moduleService.findById(moduleId).get();
        ModelAndView modelAndView = new ModelAndView("questionbank/question/questionEdit");
        modelAndView.addObject("questionEdit",questionService.findById(id));
        modelAndView.addObject("program",program);
        modelAndView.addObject("module",module);
        return modelAndView;
    }

    @PostMapping("/questionbank/program/{programId}/module/{moduleId}/question/edit")
    public ModelAndView questionEdit(@ModelAttribute("questionEdit") Question question,@PathVariable("programId") Long programId, @PathVariable Long moduleId) {
        Module module = moduleService.findById(moduleId).get();
        Program program = programService.findById(programId).get();
        if(module != null){
            question.setModule(module);
        }
        questionService.save(question);
        ModelAndView modelAndView = new ModelAndView("questionbank/question/questionEdit");
        modelAndView.addObject("questionEdit", question);
        modelAndView.addObject("program",program);
        modelAndView.addObject("module",module);
        modelAndView.addObject("message", "Question updated successfully");
        return modelAndView;
    }

    @GetMapping("/questionbank/program/{programId}/module/{moduleId}/question/delete/{id}")
    public String questionDeleteForm(@PathVariable("programId") Long programId,@PathVariable Long moduleId, @PathVariable Long id) {
        questionService.remove(id);
        return "redirect:/admin/questionbank/program/{programId}/module/{moduleId}/question";
    }

    @GetMapping("/questionbank/program/{programId}/module/{moduleId}/question/{id}/answer")
    public ModelAndView answerList(@PathVariable("programId") Long programId, @PathVariable Long moduleId,@PathVariable("id") Long id){
        Program program = programService.findById(programId).get();
        Module module = moduleService.findById(moduleId).get();
        Question question = questionService.findById(id).get();
        if (question == null){
            return new ModelAndView("/error.404");
        }
        Iterable<Answer> answers = answerService.findAllByQuestion(question);
        ModelAndView modelAndView = new ModelAndView("questionbank/answer/answerList");
        modelAndView.addObject("program",program);
        modelAndView.addObject("module",module);
        modelAndView.addObject("question",question);
        modelAndView.addObject("answers",answers);
        return modelAndView;
    }

    @GetMapping("/questionbank/program/{programId}/module/{moduleId}/question/{questionId}/answer/create")
    public ModelAndView answerCreateForm(@PathVariable("programId") Long programId, @PathVariable Long moduleId,@PathVariable Long questionId){
        Program program = programService.findById(programId).get();
        Module module = moduleService.findById(moduleId).get();
        Question question = questionService.findById(questionId).get();
        ModelAndView modelAndView = new ModelAndView("questionbank/answer/answerCreate");
        modelAndView.addObject("program",program);
        modelAndView.addObject("module",module);
        modelAndView.addObject("question",question);
        modelAndView.addObject("answerCreate",new Answer());
        return modelAndView;
    }

    @PostMapping("/questionbank/program/{programId}/module/{moduleId}/question/{questionId}/answer/create")
    public ModelAndView answerCreate(@ModelAttribute("answerCreate") Answer answer,@PathVariable("programId") Long programId, @PathVariable Long moduleId, @PathVariable Long questionId){
        Program program = programService.findById(programId).get();
        Module module = moduleService.findById(moduleId).get();
        Question question = questionService.findById(questionId).get();
        if(question != null){
            answer.setQuestion(question);
        }
        answerService.save(answer);
        ModelAndView modelAndView = new ModelAndView("questionbank/answer/answerCreate");
        modelAndView.addObject("answerCreate",new Answer());
        modelAndView.addObject("program",program);
        modelAndView.addObject("module",module);
        modelAndView.addObject("question",question);
        modelAndView.addObject("message", "answer create successfully");
        return modelAndView;
    }

    @GetMapping("/questionbank/program/{programId}/module/{moduleId}/question/{questionId}/answer/edit/{id}")
    public ModelAndView answerEditForm(@PathVariable("programId") Long programId, @PathVariable Long moduleId,@PathVariable Long questionId,@PathVariable Long id) {
        Program program = programService.findById(programId).get();
        Module module = moduleService.findById(moduleId).get();
        Question question = questionService.findById(questionId).get();
        ModelAndView modelAndView = new ModelAndView("questionbank/answer/answerEdit");
        modelAndView.addObject("answerEdit",answerService.findById(id));
        modelAndView.addObject("program",program);
        modelAndView.addObject("module",module);
        modelAndView.addObject("question",question);
        return modelAndView;
    }

    @PostMapping("/questionbank/program/{programId}/module/{moduleId}/question/{questionId}/answer/edit")
    public ModelAndView answerEdit(@ModelAttribute("answerEdit") Answer answer,@PathVariable("programId") Long programId, @PathVariable Long moduleId, @PathVariable Long questionId) {
        Program program = programService.findById(programId).get();
        Module module = moduleService.findById(moduleId).get();
        Question question = questionService.findById(questionId).get();
        if(question != null) {
            answer.setQuestion(question);
        }
        answerService.save(answer);
        ModelAndView modelAndView = new ModelAndView("questionbank/answer/answerEdit");
        modelAndView.addObject("program",program);
        modelAndView.addObject("module",module);
        modelAndView.addObject("question",question);
        modelAndView.addObject("answerEdit", answer);
        modelAndView.addObject("message", "answer updated successfully");
        return modelAndView;
    }

    @GetMapping("/questionbank/program/{programId}/module/{moduleId}/question/{questionId}/answer/delete/{id}")
    public String answerDeleteForm(@PathVariable("programId") Long programId, @PathVariable Long moduleId,@PathVariable Long questionId,@PathVariable Long id) {
        answerService.remove(id);
        return "redirect:/admin/questionbank/program/{programId}/module/{moduleId}/question/{questionId}/answer";
    }
}
