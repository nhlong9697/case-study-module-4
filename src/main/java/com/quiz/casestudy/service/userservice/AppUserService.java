package com.quiz.casestudy.service.userservice;


import com.quiz.casestudy.model.AppUser;
import com.quiz.casestudy.repository.IAppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AppUserService implements IAppUserService, UserDetailsService {
    @Autowired
    private IAppUserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Optional<AppUser >getUserByEmail(String username) {
        return userRepository.findByEmail(username);
    }

    @Override
    public boolean existByEmail(String email) {
        boolean exist = userRepository.existsByEmail(email);
        return exist;
    }

    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        AppUser appUser = this.getUserByEmail(email).get();
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(appUser.getRole());

        UserDetails userDetails = new User(appUser.getEmail(),
                appUser.getPassword(),
                authorities);
        return userDetails;
    }

    @Override
    public Iterable<AppUser> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Optional<AppUser> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public AppUser save(AppUser appUser) {
        return userRepository.save(appUser);
    }

    @Override
    public void remove(Long id) {
        userRepository.deleteById(id);
    }

}
