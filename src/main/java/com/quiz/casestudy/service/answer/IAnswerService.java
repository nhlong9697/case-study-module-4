package com.quiz.casestudy.service.answer;

import com.quiz.casestudy.model.Answer;
import com.quiz.casestudy.model.Question;
import com.quiz.casestudy.service.IService;

public interface IAnswerService extends IService<Answer> {
    Iterable<Answer> findAllByQuestion(Question question);
}
