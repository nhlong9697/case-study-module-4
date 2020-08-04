package com.quiz.casestudy.service.student;

import com.quiz.casestudy.model.AppRole;
import com.quiz.casestudy.model.Classes;
import com.quiz.casestudy.model.Student;
import com.quiz.casestudy.repository.IAppRoleRepository;
import com.quiz.casestudy.repository.IStudentRepository;
import com.quiz.casestudy.service.userservice.IAppRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
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
        student.getAppUser().setRole(appRoleRepository.findAppRoleByAuthority("ROLE_STUDENT").get());
        //encode password
        return studentRepository.save(student);
    }

    @Override
    public void remove(Long id) {
        studentRepository.deleteById(id);
    }

    @Override
    public Page<Student> findAllByClasses(Classes classes, Pageable pageable) {
        return studentRepository.findAllByClasses(classes, pageable);
    }

    @Override
    public Page<Student> findAllByNameContaining(String name, Pageable pageble) {
        return studentRepository.findAllByNameContaining(name,pageble);
    }

    @Override
    public Page<Student> findAll(Pageable pageble) {
        return studentRepository.findAll(pageble);
    }
}
