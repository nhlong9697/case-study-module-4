package com.quiz.casestudy.service.classes;

import com.quiz.casestudy.model.Classes;
import com.quiz.casestudy.model.Program;
import com.quiz.casestudy.repository.IClassesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClassesService implements IClassesService{
    @Autowired
    private IClassesRepository classesRepository;
    @Override
    public Iterable<Classes> findAll() {
       return classesRepository.findAll();
    }

    @Override
    public Optional<Classes> findById(Long id) {
        return classesRepository.findById(id);
    }

    @Override
    public Classes save(Classes classes) {
        return classesRepository.save(classes);
    }

    @Override
    public void remove(Long id) {
        classesRepository.deleteById(id);
    }

    @Override
    public Iterable<Classes> findByProgram(Program program) {
       return classesRepository.findAllByProgram(program);
    }
}
