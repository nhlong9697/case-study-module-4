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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
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
    private IAppRoleService appRoleService;

    @ModelAttribute("programs")
    public Iterable<Program> programs(){
        return programService.findAll();
    }

    @ModelAttribute("classesList")
    public Iterable<Classes> classes() {
        return classesService.findAll();
    }

    @GetMapping("/classes")
    public ModelAndView classList(@RequestParam("s")Optional<String>s,
                                  @PageableDefault(size = 6, direction = Sort.Direction.ASC, sort = "id") Pageable pageable) {
        Page<Classes> classes;
        ModelAndView modelAndView = new ModelAndView("studentmanagement/classes/classesList");
        if (s.isPresent()){
            classes = classesService.findAllByNameContaining(s.get(), pageable);
            modelAndView.addObject("s", s.get());
        }else {
            classes = classesService.findAll(pageable);
        }
        modelAndView.addObject("classList", classes);
        return modelAndView;
    }

    @GetMapping("/classes/create")
    public ModelAndView createClassForm() {
        ModelAndView modelAndView = new ModelAndView("studentmanagement/classes/classesCreate");
        modelAndView.addObject("newClass", new Classes());
        return modelAndView;
    }

    @PostMapping("/classes/create")
    public ModelAndView classesCreate(@ModelAttribute("newClass") @Validated Classes classes,
                                      BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView("studentmanagement/classes/classesCreate");
        if (bindingResult.hasFieldErrors()) {
            return modelAndView;
        }
        classesService.save(classes);
        modelAndView.addObject("classes", new Classes());
        modelAndView.addObject("success", "New class created");
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
        return "redirect:/admin/studentmanagement/classes";
    }

    @GetMapping("/student")
    public ModelAndView allStudentList(@RequestParam("s")Optional<String>s,
                                  @PageableDefault(size = 6, direction = Sort.Direction.ASC, sort = "id") Pageable pageable) {
        Page<Student> students;
        ModelAndView modelAndView = new ModelAndView("studentmanagement/student/allStudentList");
        if (s.isPresent()){
            students = studentService.findAllByNameContaining(s.get(), pageable);
            modelAndView.addObject("s", s.get());
        }else {
            students = studentService.findAll(pageable);
        }
        modelAndView.addObject("allStudentList", students);
        return modelAndView;
    }

    @GetMapping("/classes/{id}/student")
    public ModelAndView studentList(@RequestParam("s")Optional<String>s,
                                    @PageableDefault(size = 6, direction = Sort.Direction.ASC, sort = "id") Pageable pageable
            ,@PathVariable("id") Long id){
        Page<Student> students;
        ModelAndView modelAndView = new ModelAndView("studentmanagement/student/studentList");
        Classes classes = classesService.findById(id).get();
        if (classes == null){
            return new ModelAndView("/error.404");
        }else {
            students = studentService.findAllByClasses(classes,pageable);
        }if (s.isPresent()){
            students = studentService.findAllByNameContaining(s.get(), pageable);
            modelAndView.addObject("s", s.get());
        }
        modelAndView.addObject("classes",classes);
        modelAndView.addObject("studentList",students);
        return modelAndView;
    }
    @GetMapping("/student/create")
    public ModelAndView showCreateStudentForm(){
        ModelAndView modelAndView = new ModelAndView("studentmanagement/student/studentCreate");
        modelAndView.addObject("newStudent",new Student());
        return modelAndView;
    }
    @PostMapping("/getClassesByProgram")
    public ResponseEntity<Iterable<Classes>> getClassesByProgram(@RequestBody Program program) {
        return new ResponseEntity<>(classesService.findByProgram(program), HttpStatus.OK);
    }

    @PostMapping("/student/create")
    public ModelAndView createStudent(@ModelAttribute("newStudent") @Validated Student student,
                                      BindingResult bindingResult){
        //save file to student

        ModelAndView modelAndView = new ModelAndView("studentmanagement/student/studentCreate");
        if (bindingResult.hasFieldErrors()) {
            return modelAndView;
        }
        uploadFile(student);
        student.getAppUser().setPassword(passwordEncoder.encode(student.getAppUser().getPassword()));
        studentService.save(student);


        modelAndView.addObject("newStudent",new Student());
        modelAndView.addObject("success", "Student create successfully");

        return modelAndView;
    }

    @GetMapping("/student/edit/{studentId}")
    public ModelAndView studentEditForm(@PathVariable Long studentId) {
        Optional<Student> student = studentService.findById(studentId);
        if (student.isPresent()) {
            ModelAndView modelAndView = new ModelAndView("/studentmanagement/student/studentEdit");
            modelAndView.addObject("student",student);
            return modelAndView;
        } else {
            return new ModelAndView("error.404");
        }
    }

    @PostMapping("/student/edit/{studentId}")
    public ModelAndView studentEdit(@ModelAttribute Student student,
                                   @PathVariable Long studentId) {

        uploadFile(student);
        studentService.save(student);

        ModelAndView modelAndView = new ModelAndView("/studentmanagement/student/studentEdit");
        modelAndView.addObject("student", student);
        modelAndView.addObject("success", "student updated successfully");
        return modelAndView;
    }

    private void uploadFile(@ModelAttribute Student student) {
        MultipartFile file = student.getImage();
        String fileName = file.getOriginalFilename();
        String fileUpload = environment.getProperty("upload.path");
        student.setAvatar(fileName);
        try {
            FileCopyUtils.copy(file.getBytes(), new File(fileUpload + fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/student/delete/{studentId}")
    public String moduleDeleteForm(@PathVariable Long studentId) {
        studentService.remove(studentId);
        return "redirect:/admin/studentmanagement/student";
    }
}
