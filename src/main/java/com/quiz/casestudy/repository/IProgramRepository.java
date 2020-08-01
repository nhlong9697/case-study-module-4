package com.quiz.casestudy.repository;

import com.quiz.casestudy.model.Program;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface IProgramRepository extends CrudRepository<Program, Long> {
}
