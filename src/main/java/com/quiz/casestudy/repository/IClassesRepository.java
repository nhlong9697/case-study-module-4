package com.quiz.casestudy.repository;

import com.quiz.casestudy.model.Classes;
import com.quiz.casestudy.model.Program;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface IClassesRepository extends PagingAndSortingRepository<Classes,Long> {
    Iterable<Classes> findAllByProgram(Program program);
    boolean existsByName(String name);
}
