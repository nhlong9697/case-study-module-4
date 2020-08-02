package com.quiz.casestudy.service.userservice;

import com.quiz.casestudy.model.AppRole;
import com.quiz.casestudy.service.IService;

import java.util.Optional;

public interface IAppRoleService extends IService<AppRole> {
    Optional<AppRole >findAppRoleByAuthority(String authority);
}
