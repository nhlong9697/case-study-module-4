package com.quiz.casestudy.service.assign;

import com.quiz.casestudy.model.QuizAssignment;
import com.quiz.casestudy.repository.IQuizAssignmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class QuizAssignmentService implements IQuizAssignmentService{
    @Autowired
    private IQuizAssignmentRepository quizAssignmentRepository;
    @Override
    public Iterable<QuizAssignment> findAll() {
       return quizAssignmentRepository.findAll();
    }

    @Override
    public Optional<QuizAssignment> findById(Long id) {
       return quizAssignmentRepository.findById(id);
    }

    @Override
    public QuizAssignment save(QuizAssignment quizAssignment) {
        return quizAssignmentRepository.save(quizAssignment);
    }

    @Override
    public void remove(Long id) {
        quizAssignmentRepository.deleteById(id);
    }
}
