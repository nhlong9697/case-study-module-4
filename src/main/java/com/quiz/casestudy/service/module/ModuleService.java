package com.quiz.casestudy.service.module;

import com.quiz.casestudy.model.Module;
import com.quiz.casestudy.model.Program;
import com.quiz.casestudy.repository.IModuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class ModuleService implements IModuleService {
    @Autowired
    private IModuleRepository moduleRepository;

    @Override
    public Iterable<Module> findAll() {
        return moduleRepository.findAll();
    }

    @Override
    public Optional<Module> findById(Long id) {
        return moduleRepository.findById(id);
    }

    @Override
    public Module save(Module module) {
        return moduleRepository.save(module);
    }

    @Override
    public void remove(Long id) {
        moduleRepository.deleteById(id);
    }

    @Override
    public Iterable<Module> findAllByProgram(Program program){
        return moduleRepository.findAllByProgram(program);
    }

}
