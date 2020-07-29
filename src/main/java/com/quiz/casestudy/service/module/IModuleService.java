package com.quiz.casestudy.service.module;

import com.quiz.casestudy.model.Module;
import com.quiz.casestudy.model.Program;
import com.quiz.casestudy.service.IService;

public interface IModuleService extends IService<Module> {
    Iterable<Module> findAllByProgram(Program program);
}
