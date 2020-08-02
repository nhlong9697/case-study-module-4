package com.quiz.casestudy.repository;

import com.quiz.casestudy.model.AppUser;
import org.springframework.data.repository.CrudRepository;

public interface IAppUserRepository extends CrudRepository<AppUser, Long> {
    AppUser findByEmail(String email);
    boolean existsByEmail(String email);
}
