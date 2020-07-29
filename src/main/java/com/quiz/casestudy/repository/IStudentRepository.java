package com.quiz.casestudy.repository;

import com.quiz.casestudy.model.Student;
import org.springframework.data.repository.CrudRepository;

public interface IStudentRepository extends CrudRepository<Student, Long> {
}
