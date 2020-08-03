package com.quiz.casestudy.repository;

import com.quiz.casestudy.model.Module;
import com.quiz.casestudy.model.Program;
import com.quiz.casestudy.model.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IQuestionRepository extends JpaRepository<Question, Long> {
    Page<Question> findAllByModule(Module module, Pageable pageble);
    Page<Question> findAllByNameContaining(String name, Pageable pageble);
    Long countAllByType(int type);
    Page<Question> findAllByType(int type, Pageable pageable);
}
