package com.quiz.casestudy.repository;

import com.quiz.casestudy.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IQuestionRepository extends JpaRepository<Question, Long> {
}
