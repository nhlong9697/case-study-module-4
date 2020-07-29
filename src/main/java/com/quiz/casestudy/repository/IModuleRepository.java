package com.quiz.casestudy.repository;

import com.quiz.casestudy.model.Module;
import com.quiz.casestudy.model.Program;
import org.springframework.data.jpa.repository.JpaRepository;


public interface IModuleRepository extends JpaRepository<Module, Long> {
    Iterable<Module> findAllByProgram(Program program);
}
