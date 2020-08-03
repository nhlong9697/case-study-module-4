package com.quiz.casestudy.repository;

import com.quiz.casestudy.model.Quiz;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface IQuizRepository extends PagingAndSortingRepository<Quiz, Long> {
}
