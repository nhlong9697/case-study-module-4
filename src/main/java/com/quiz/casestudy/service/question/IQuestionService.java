package com.quiz.casestudy.service.question;

import com.quiz.casestudy.model.Module;
import com.quiz.casestudy.model.Program;
import com.quiz.casestudy.model.Question;
import com.quiz.casestudy.service.IService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Set;

public interface IQuestionService extends IService<Question> {
    Page<Question> findAllByModule(Module module,Pageable pageable);
    Page<Question> findAllByNameContaining(String name, Pageable pageable);
    Long countAllByType(int type);
    Page<Question> findAllByType(int type, Pageable pageable);
    Set<Question> getRandomQuestionSetByType(int type, int amount);
}
