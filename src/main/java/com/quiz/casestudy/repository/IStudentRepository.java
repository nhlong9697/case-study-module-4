package com.quiz.casestudy.repository;

import com.quiz.casestudy.model.Classes;
import com.quiz.casestudy.model.Question;
import com.quiz.casestudy.model.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface IStudentRepository extends PagingAndSortingRepository<Student, Long> {
    Page<Student> findAllByClasses(Classes classes, Pageable pageable);
    Page<Student> findAllByNameContaining(String name, Pageable pageble);
    Page<Student> findAll(Pageable pageble);
}
