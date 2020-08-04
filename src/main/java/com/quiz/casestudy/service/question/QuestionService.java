package com.quiz.casestudy.service.question;

import com.quiz.casestudy.model.Module;
import com.quiz.casestudy.model.Question;
import com.quiz.casestudy.repository.IQuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class QuestionService implements IQuestionService {
    @Autowired
    private IQuestionRepository questionRepository;

    @Override
    public Iterable<Question> findAll() {
        return questionRepository.findAll();
    }

    @Override
    public Optional<Question> findById(Long id) {
        return questionRepository.findById(id);
    }

    @Override
    public Question save(Question question) {
        return questionRepository.save(question);
    }

    @Override
    public void remove(Long id) {
        questionRepository.deleteById(id);
    }

    @Override
    public Page<Question> findAllByModule(Module module,Pageable pageable) {
        return questionRepository.findAllByModule(module, pageable);
    }

    @Override
    public Page<Question> findAllByNameContaining(String name, Pageable pageable) {
        return questionRepository.findAllByNameContaining(name, pageable);
    }

    @Override
    public Long countAllByTypeAndModule(int type, Module module) {
       return questionRepository.countAllByTypeAndModule(type,module);
    }

    @Override
    public Page<Question> findAllByTypeAndModule(int type,Module module, Pageable pageable) {
        return questionRepository.findAllByTypeAndModule(type,module, pageable);
    }

    @Override
    public Set<Question> getRandomQuestionSetByTypeAndModule(int type, Module module, int amount) {
        Set<Question> questionSet = new HashSet<>();
        Long questionCountByType = countAllByTypeAndModule(type,module);
        int index = (int) (Math.random() * questionCountByType);
        Page<Question> questionPage = questionRepository.findAllByTypeAndModule(type,module,
                PageRequest.of(index,1));
        while (questionSet.size() < amount) {
            Question question = null;
            if (questionPage.hasContent()) {
                question = questionPage.getContent().get(0);
            }
            questionSet.add(question);
        }
        return questionSet;
    }
}
