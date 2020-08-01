package com.quiz.casestudy.service.classes;

import com.quiz.casestudy.model.Classes;
import com.quiz.casestudy.model.Program;
import com.quiz.casestudy.service.IService;

public interface IClassesService extends IService<Classes> {
    Iterable<Classes> findByProgram(Program program);
}
