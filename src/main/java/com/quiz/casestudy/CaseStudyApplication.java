package com.quiz.casestudy;

import com.quiz.casestudy.model.AppRole;
import com.quiz.casestudy.service.userservice.IAppRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class CaseStudyApplication {
    @Autowired
    private IAppRoleService appRoleService;

    @PostConstruct
    public void setRol(){
        appRoleService.save(new AppRole("ROLE_ADMIN"));
        appRoleService.save(new AppRole("ROLE_STAFF"));
        appRoleService.save(new AppRole("ROLE_STUDENT"));
    }

    public static void main(String[] args) {
        SpringApplication.run(CaseStudyApplication.class, args);
    }

}
