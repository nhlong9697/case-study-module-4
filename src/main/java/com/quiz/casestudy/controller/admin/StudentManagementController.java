package com.quiz.casestudy.controller.admin;

import com.quiz.casestudy.model.Classes;
import com.quiz.casestudy.model.Module;
import com.quiz.casestudy.model.Program;
import com.quiz.casestudy.model.Student;
import com.quiz.casestudy.service.classes.IClassesService;
import com.quiz.casestudy.service.program.IProgramService;
import com.quiz.casestudy.service.student.IStudentService;
import com.quiz.casestudy.service.userservice.IAppRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;
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

    @Autowired
    private IAppRoleService appRoleService;

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
    public ModelAndView moduleList(@PathVariable Long classesId){
        Classes classes = classesService.findById(classesId).get();
        if (classes == null){
            return new ModelAndView("/error.404");
        }
        Iterable<Student> studentList = studentService.findAllByClasses(classes);
        ModelAndView modelAndView = new ModelAndView("/studentmanagement/student/studentList");
        modelAndView.addObject("studentList",studentList);
        modelAndView.addObject("classes",classes);
        return modelAndView;
    }

    @GetMapping("/classes/{classesId}/create")
    public ModelAndView moduleCreateForm(@PathVariable Long classesId){
        Classes classes = classesService.findById(classesId).get();
        ModelAndView modelAndView = new ModelAndView("/studentmanagement/student/studentCreate");
        modelAndView.addObject("classes",classes);
        modelAndView.addObject("newStudent",new Student());
        return modelAndView;
    }

    @PostMapping("/classes/{classesId}/module/create")
    public ModelAndView createStudent(@ModelAttribute("newStudent") Student student,
                                     @PathVariable Long classesId){
        //save file to student
        MultipartFile file = student.getImage();
        String fileName = file.getOriginalFilename();
        String fileUpload = environment.getProperty("upload.path");
        Classes classes = classesService.findById(classesId).get();
        student.setAvatar(fileName);
        try {
            FileCopyUtils.copy(file.getBytes(), new File(fileUpload + fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }

        //save class to student
        if(classes != null){
            student.setClasses(classes);
        }

        studentService.save(student);

        ModelAndView modelAndView = new ModelAndView("questionbank/module/moduleCreate");

        modelAndView.addObject("moduleCreate",new Student());
        modelAndView.addObject("classes",classes);
        modelAndView.addObject("message", "Student create successfully");

        return modelAndView;
    }

    @GetMapping("/classes/{classesId}/module/edit/{studentId}")
    public ModelAndView studentEditForm(@PathVariable Long classesId,
                                        @PathVariable Long studentId) {
        Classes classes = classesService.findById(classesId).get();
        ModelAndView modelAndView = new ModelAndView("/studentmanagement/classes/classesEdit");
        modelAndView.addObject("classes",classes);
        modelAndView.addObject("studentEdit",studentService.findById(studentId));
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
