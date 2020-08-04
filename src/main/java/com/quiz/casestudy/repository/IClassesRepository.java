package com.quiz.casestudy.repository;

import com.quiz.casestudy.model.Classes;
import com.quiz.casestudy.model.Program;
import com.quiz.casestudy.model.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface IClassesRepository extends PagingAndSortingRepository<Classes,Long> {
    Iterable<Classes> findAllByProgram(Program program);
    boolean existsByName(String name);
    Page<Classes> findAllByNameContaining(String name, Pageable pageble);
    Page<Classes> findAll(Pageable pageable);
}
