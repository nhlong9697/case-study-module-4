package com.quiz.casestudy.repository;

import com.quiz.casestudy.model.Program;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IProgramRepository extends JpaRepository<Program, Long> {
}
