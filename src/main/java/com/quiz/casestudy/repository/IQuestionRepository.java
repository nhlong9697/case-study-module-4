package com.quiz.casestudy.repository;

import com.quiz.casestudy.model.Module;
import com.quiz.casestudy.model.Program;
import com.quiz.casestudy.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface IQuestionRepository extends PagingAndSortingRepository<Question, Long> {
    Iterable<Question> findAllByModule(Module module);
}
