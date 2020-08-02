package com.quiz.casestudy.repository;

import com.quiz.casestudy.model.AppRole;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface IAppRoleRepository extends CrudRepository<AppRole,Long> {
    Optional<AppRole> findAppRoleByAuthority(String authority);
}
