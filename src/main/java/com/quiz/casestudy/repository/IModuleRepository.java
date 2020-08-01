package com.quiz.casestudy.repository;

import com.quiz.casestudy.model.Module;
import com.quiz.casestudy.model.Program;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;


public interface IModuleRepository extends CrudRepository<Module, Long> {
    Iterable<Module> findAllByProgram(Program program);
}
