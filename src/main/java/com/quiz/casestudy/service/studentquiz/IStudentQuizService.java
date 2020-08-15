package com.quiz.casestudy.service.studentquiz;

import com.quiz.casestudy.model.Student;
import com.quiz.casestudy.model.StudentQuiz;
import com.quiz.casestudy.service.IService;

public interface IStudentQuizService extends IService<StudentQuiz> {
    Iterable<StudentQuiz> findAllByStudent(Student student);
}
