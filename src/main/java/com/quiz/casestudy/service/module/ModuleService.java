package com.quiz.casestudy.service.module;

import com.quiz.casestudy.model.Module;
import com.quiz.casestudy.model.Program;
import com.quiz.casestudy.repository.IModuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ModuleService implements IModuleService {
    @Autowired
    private IModuleRepository moduleRepository;

    @Override
    public Iterable<Module> findAll() {
        return moduleRepository.findAll();
    }

    @Override
    public Optional<Module> findById(Long moduleId) {
        return moduleRepository.findById(moduleId);
    }

    @Override
    public Module save(Module module) {
        return moduleRepository.save(module);
    }

    @Override
    public void remove(Long moduleId) {
        moduleRepository.deleteById(moduleId);
    }

    @Override
    public Iterable<Module> findAllByProgram(Program program){
        return moduleRepository.findAllByProgram(program);
    }

}
