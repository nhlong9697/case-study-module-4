package com.quiz.casestudy.service.program;

import com.quiz.casestudy.model.Program;
import com.quiz.casestudy.repository.IProgramRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProgramService implements IProgramService {
    @Autowired
    private IProgramRepository programRepository;

    @Override
    public Iterable<Program> findAll() {
        return programRepository.findAll();
    }

    @Override
    public Optional<Program> findById(Long programId) {
        return programRepository.findById(programId);
    }

    @Override
    public Program save(Program program) {
        return programRepository.save(program);
    }

    @Override
    public void remove(Long programId) {
        programRepository.deleteById(programId);
    }
}
