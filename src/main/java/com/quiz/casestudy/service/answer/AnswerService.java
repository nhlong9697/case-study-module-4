package com.quiz.casestudy.service.answer;

import com.quiz.casestudy.model.Answer;
import com.quiz.casestudy.repository.IAnswerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AnswerService implements IAnswerService {
    @Autowired
    private IAnswerRepository answerRepository;

    @Override
    public Iterable<Answer> findAll() {
        return answerRepository.findAll();
    }

    @Override
    public Optional<Answer> findById(Long answerId) {
        return answerRepository.findById(answerId);
    }

    @Override
    public Answer save(Answer answer) {
        return answerRepository.save(answer);
    }

    @Override
    public void remove(Long answerId) {
        answerRepository.deleteById(answerId);
    }
}
