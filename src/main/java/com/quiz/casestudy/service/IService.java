package com.quiz.casestudy.service;

import com.quiz.casestudy.model.Module;

import java.util.Optional;

public interface IService<T> {
    Iterable<T> findAll();
    Optional<T> findById(Long id);
    T save (T t);
    void remove (Long id);
}
