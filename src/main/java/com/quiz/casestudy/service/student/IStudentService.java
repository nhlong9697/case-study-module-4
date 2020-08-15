package com.quiz.casestudy.service.student;


import com.quiz.casestudy.model.AppUser;
import com.quiz.casestudy.model.Classes;
import com.quiz.casestudy.model.Student;
import com.quiz.casestudy.service.IService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IStudentService extends IService<Student> {
    Page<Student> findAllByClasses(Classes classes, Pageable pageable);
    Page<Student> findAllByNameContaining(String name, Pageable pageble);
    Page<Student> findAll(Pageable pageble);
    Student findByAppUser(AppUser appUser);
}
