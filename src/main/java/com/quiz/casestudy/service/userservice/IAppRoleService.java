package com.quiz.casestudy.service.userservice;

import com.quiz.casestudy.model.AppRole;
import com.quiz.casestudy.service.IService;

public interface IAppRoleService extends IService<AppRole> {
    AppRole findAppRoleByAuthority(String authority);
}
