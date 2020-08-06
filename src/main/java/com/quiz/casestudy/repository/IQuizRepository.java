package com.quiz.casestudy.repository;

import com.quiz.casestudy.model.Module;
import com.quiz.casestudy.model.Quiz;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface IQuizRepository extends PagingAndSortingRepository<Quiz, Long> {
    @Query(value = "SELECT * FROM quiz WHERE quiz.id = (SELECT DISTINCT quiz_id FROM " +
            "question_quiz WHERE question_quiz.question_id IN (SELECT id " +
            "FROM question WHERE module_id = :#{#module.id})" +
            " ) ",
            nativeQuery = true)
    Iterable<Quiz> findQuizzesByModule(@Param("module") Module module);
}
