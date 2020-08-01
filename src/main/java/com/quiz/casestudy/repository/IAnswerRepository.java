package com.quiz.casestudy.repository;

import com.quiz.casestudy.model.Answer;
import com.quiz.casestudy.model.Module;
import com.quiz.casestudy.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

public interface IAnswerRepository extends CrudRepository<Answer, Long> {
    Iterable<Answer> findAllByQuestion(Question question);
}
