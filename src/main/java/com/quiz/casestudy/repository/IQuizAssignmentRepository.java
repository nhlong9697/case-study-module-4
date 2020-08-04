package com.quiz.casestudy.repository;

import com.quiz.casestudy.model.QuizAssignment;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface IQuizAssignmentRepository extends PagingAndSortingRepository<QuizAssignment,
        Long> {
}
