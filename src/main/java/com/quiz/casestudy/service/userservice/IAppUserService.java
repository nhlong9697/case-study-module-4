package com.quiz.casestudy.service.userservice;


import com.quiz.casestudy.model.AppUser;
import com.quiz.casestudy.service.IService;
import com.quiz.casestudy.service.userservice.exception.UserAlreadyExistException;

public interface IAppUserService extends IService<AppUser> {
    AppUser getUserByEmail(String username);
}
