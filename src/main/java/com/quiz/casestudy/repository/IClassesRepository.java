package com.quiz.casestudy.repository;

import com.quiz.casestudy.model.Classes;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface IClassesRepository extends PagingAndSortingRepository<Classes,Long> {
}
