package com.quiz.casestudy.service.question;

import com.quiz.casestudy.model.Module;
import com.quiz.casestudy.model.Program;
import com.quiz.casestudy.model.Question;
import com.quiz.casestudy.service.IService;

public interface IQuestionService extends IService<Question> {
    Iterable<Question> findAllByModule(Module module);
}
