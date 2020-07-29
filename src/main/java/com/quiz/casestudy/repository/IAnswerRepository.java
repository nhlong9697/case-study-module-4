package com.quiz.casestudy.repository;

import com.quiz.casestudy.model.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IAnswerRepository extends JpaRepository<Answer, Long> {
}
