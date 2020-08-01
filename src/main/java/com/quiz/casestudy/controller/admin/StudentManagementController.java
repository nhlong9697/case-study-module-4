package com.quiz.casestudy.controller.admin;

import com.quiz.casestudy.model.Classes;
import com.quiz.casestudy.model.Module;
import com.quiz.casestudy.model.Program;
import com.quiz.casestudy.model.Student;
import com.quiz.casestudy.service.classes.IClassesService;
import com.quiz.casestudy.service.program.IProgramService;
import com.quiz.casestudy.service.student.IStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Locale;
import java.util.Optional;

@Controller
@RequestMapping("/admin/studentmanagement")
public class StudentManagementController {
    @Autowired
    private IClassesService classesService;
    @Autowired
    private IProgramService programService;
    @Autowired
    private IStudentService studentService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private Environment environment;

    @Autowired
    private MessageSource messageSource;

    @ModelAttribute("programs")
    public Iterable<Program> programs(){
        return programService.findAll();
    }

    @GetMapping("/classes")
    public ModelAndView classList() {
        ModelAndView modelAndView = new ModelAndView("studentmanagement/classes/classesList");
        Iterable<Classes> classList = classesService.findAll();
        modelAndView.addObject("classList", classList);
        return modelAndView;
    }

    @GetMapping("/classes/create")
    public ModelAndView createClassForm() {
        ModelAndView modelAndView = new ModelAndView("studentmanagement/classes/classesCreate");
        modelAndView.addObject("newClass", new Classes());
        return modelAndView;
    }

    @PostMapping("/classes/create")
    public ModelAndView classesCreate(@ModelAttribute Classes classes) {
        ModelAndView modelAndView = new ModelAndView("studentmanagement/classes/classesCreate");
        classesService.save(classes);
        modelAndView.addObject("classes", new Classes());
        modelAndView.addObject("success", "Success register");
        return modelAndView;
    }
    @GetMapping("/classes/edit/{id}")
    public ModelAndView showEditForm(@PathVariable Long id){
        Optional<Classes> classes = classesService.findById(id);
        if(classes.isPresent()) {
            ModelAndView modelAndView = new ModelAndView("studentmanagement/classes/classesEdit");
            modelAndView.addObject("classes", classes.get());
            return modelAndView;

        }else {
            return new ModelAndView("/error.404");
        }
    }

    @PostMapping("/classes/edit/{id}")
    public ModelAndView updateClasses(@ModelAttribute("classes") Classes classes){
        classesService.save(classes);
        ModelAndView modelAndView = new ModelAndView("studentmanagement/classes/classesEdit");
        modelAndView.addObject("classes", classes);
        modelAndView.addObject("success", "Classes updated successfully");
        return modelAndView;
    }

    @GetMapping("/classes/delete/{classesId}")
    public String deleteClasses(@PathVariable Long classesId){
        classesService.remove(classesId);
        return "redirect:admin/studentmanagement/classes";
    }

    @GetMapping("/classes/{classesId}/student")
    public ModelAndView moduleList(@PathVariable("classesId") Long classesId){
        Classes classes = classesService.findById(classesId).get();
        if (classes == null){
            return new ModelAndView("/error.404");
        }
        Iterable<Student> students = studentService.findAllByClasses(classes);
        ModelAndView modelAndView = new ModelAndView("/studentmanagement/module/moduleList");
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
}
