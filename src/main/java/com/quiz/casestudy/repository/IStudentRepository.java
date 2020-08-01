package com.quiz.casestudy.repository;

import com.quiz.casestudy.model.Classes;
import com.quiz.casestudy.model.Student;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface IStudentRepository extends PagingAndSortingRepository<Student, Long> {
    Iterable<Student> findAllByClasses(Classes classes);
}
