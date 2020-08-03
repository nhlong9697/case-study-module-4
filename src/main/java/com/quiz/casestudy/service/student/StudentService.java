package com.quiz.casestudy.service.student;

import com.quiz.casestudy.model.Classes;
import com.quiz.casestudy.model.Student;
import com.quiz.casestudy.repository.IAppRoleRepository;
import com.quiz.casestudy.repository.IStudentRepository;
import com.quiz.casestudy.service.userservice.IAppRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StudentService implements IStudentService {
    @Autowired
    private IStudentRepository studentRepository;
    @Autowired
    private IAppRoleRepository appRoleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Iterable<Student> findAll() {
        return studentRepository.findAll();
    }

    @Override
    public Optional<Student> findById(Long id) {
        return studentRepository.findById(id);
    }

    @Override
    public Student save(Student student) {
        //set role
        student.getAppUser().setRole(appRoleRepository.findAppRoleByAuthority("ROLE_USER").get());
        //encode password
        return studentRepository.save(student);
    }

    @Override
    public void remove(Long id) {
        studentRepository.deleteById(id);
    }

    @Override
    public Iterable<Student> findAllByClasses(Classes classes) {
        return studentRepository.findAllByClasses(classes);
    }
}
