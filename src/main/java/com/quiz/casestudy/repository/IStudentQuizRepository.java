package com.quiz.casestudy.repository;

import com.quiz.casestudy.model.Student;
import com.quiz.casestudy.model.StudentQuiz;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface IStudentQuizRepository extends PagingAndSortingRepository<StudentQuiz, Long> {
    Iterable<StudentQuiz> findAllByStudent(Student student);
}
