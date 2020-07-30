package com.quiz.casestudy.repository;

import com.quiz.casestudy.model.Module;
import com.quiz.casestudy.model.Program;
import com.quiz.casestudy.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IQuestionRepository extends JpaRepository<Question, Long> {
    Iterable<Question> findAllByModule(Module module);
}
