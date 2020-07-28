package com.quiz.casestudy.repository;

import com.quiz.casestudy.model.Module;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IModuleRepository extends JpaRepository<Module, Long> {
}
