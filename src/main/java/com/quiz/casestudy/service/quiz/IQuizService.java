package com.quiz.casestudy.service.quiz;

import com.quiz.casestudy.model.Module;
import com.quiz.casestudy.model.Quiz;
import com.quiz.casestudy.service.IService;

public interface IQuizService extends IService<Quiz> {
    Iterable<Quiz> findAllByModule(Module module);
}
