package com.quiz.casestudy.service.studentquiz;

import com.quiz.casestudy.model.Student;
import com.quiz.casestudy.model.StudentQuiz;
import com.quiz.casestudy.repository.IStudentQuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class StudentQuizService implements IStudentQuizService{
    @Autowired
    private IStudentQuizRepository studentQuizRepository;
    @Override
    public Iterable<StudentQuiz> findAll() {
        return studentQuizRepository.findAll();
    }

    @Override
    public Optional<StudentQuiz> findById(Long id) {
        return studentQuizRepository.findById(id);
    }

    @Override
    public StudentQuiz save(StudentQuiz studentQuiz) {
        return studentQuizRepository.save(studentQuiz);
    }

    @Override
    public void remove(Long id) {
        studentQuizRepository.deleteById(id);
    }

    @Override
    public Iterable<StudentQuiz> findAllByStudent(Student student) {
        return studentQuizRepository.findAllByStudent(student);
    }
}
