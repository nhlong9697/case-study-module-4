package com.quiz.casestudy.service.student;


import com.quiz.casestudy.model.Classes;
import com.quiz.casestudy.model.Student;
import com.quiz.casestudy.service.IService;

public interface IStudentService extends IService<Student> {
    Iterable<Student> findAllByClasses(Classes classes);
}
