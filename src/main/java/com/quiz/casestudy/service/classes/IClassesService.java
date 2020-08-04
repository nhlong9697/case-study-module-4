package com.quiz.casestudy.service.classes;

import com.quiz.casestudy.model.Classes;
import com.quiz.casestudy.model.Program;
import com.quiz.casestudy.model.Question;
import com.quiz.casestudy.service.IService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IClassesService extends IService<Classes> {
    Iterable<Classes> findByProgram(Program program);
    boolean existsByName(String name);
    Page<Classes> findAllByNameContaining(String name, Pageable pageable);
    Page<Classes> findAll(Pageable pageable);
}
