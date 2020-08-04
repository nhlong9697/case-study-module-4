package com.quiz.casestudy.service.classes;

import com.quiz.casestudy.model.Classes;
import com.quiz.casestudy.model.Program;
import com.quiz.casestudy.model.Question;
import com.quiz.casestudy.model.Student;
import com.quiz.casestudy.repository.IClassesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
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

    @Override
    public boolean existsByName(String name) {
        boolean exist = classesRepository.existsByName(name);
        return exist;
    }

    @Override
    public Page<Classes> findAllByNameContaining(String name, Pageable pageable) {
        return classesRepository.findAllByNameContaining(name, pageable);
    }

    @Override
    public Page<Classes> findAll(Pageable pageable) {
        return classesRepository.findAll(pageable);
    }


}
