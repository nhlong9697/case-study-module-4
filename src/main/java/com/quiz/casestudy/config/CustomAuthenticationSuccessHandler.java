package com.quiz.casestudy.config;

import com.quiz.casestudy.model.AppRole;
import com.quiz.casestudy.model.AppUser;
import com.quiz.casestudy.model.Student;
import com.quiz.casestudy.service.student.IStudentService;
import com.quiz.casestudy.service.userservice.IAppRoleService;
import com.quiz.casestudy.service.userservice.IAppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Autowired
    private IAppUserService appUserService;

    @Autowired
    private IAppRoleService appRoleService;

    @Autowired
    private IStudentService studentService;

    private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String email = authentication.getName();

        AppUser loginUser = appUserService.getUserByEmail(email).get();
        HttpSession session = request.getSession();

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        List<String> roles = new ArrayList<>();

        for (GrantedAuthority a : authorities) {
            roles.add(a.getAuthority());
        }

        if (roles.contains("ROLE_ADMIN")) {
            String targetUrl = "/admin";
            redirectStrategy.sendRedirect(request,response,targetUrl);
        } else if (roles.contains("ROLE_STUDENT")) {
            String targetUrl = "/home/quiz";
            Student student = studentService.findByAppUser(loginUser);
            session.setAttribute("student", student);
            redirectStrategy.sendRedirect(request,response,targetUrl);
        }
        //TODO: handle restart project bug when reset project there is no session attribute but
        // user is still logged in
    }
}
