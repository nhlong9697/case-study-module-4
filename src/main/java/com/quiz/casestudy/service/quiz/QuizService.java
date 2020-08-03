package com.quiz.casestudy.service.quiz;

import com.quiz.casestudy.model.Quiz;
import com.quiz.casestudy.repository.IQuizRepository;
import com.quiz.casestudy.service.classes.IClassesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class QuizService implements IQuizService {
    @Autowired
    private IQuizRepository quizRepository;
    @Override
    public Iterable<Quiz> findAll() {
        return quizRepository.findAll();
    }

    @Override
    public Optional<Quiz> findById(Long id) {
        return quizRepository.findById(id);
    }

    @Override
    public Quiz save(Quiz quiz) {
        return quizRepository.save(quiz);
    }

    @Override
    public void remove(Long id) {
        quizRepository.deleteById(id);
    }
}
