package com.quiz.casestudy.repository;

import com.quiz.casestudy.model.Answer;
import com.quiz.casestudy.model.Module;
import com.quiz.casestudy.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IAnswerRepository extends JpaRepository<Answer, Long> {
    Iterable<Answer> findAllByQuestion(Question question);
}
