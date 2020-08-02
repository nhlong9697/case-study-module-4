package com.quiz.casestudy.service.userservice;


import com.quiz.casestudy.model.AppUser;
import com.quiz.casestudy.service.IService;

import java.util.Optional;

public interface IAppUserService extends IService<AppUser> {
    Optional<AppUser> getUserByEmail(String email);
    boolean existByEmail(String email);
}
