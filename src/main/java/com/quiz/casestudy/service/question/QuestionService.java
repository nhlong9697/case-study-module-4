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
    public Long countAllByType(int type) {
       return questionRepository.countAllByType(type);
    }

    @Override
    public Page<Question> findAllByType(int type, Pageable pageable) {
        return questionRepository.findAllByType(type, pageable);
    }

    @Override
    public Set<Question> getRandomQuestionSetByType(int type, int amount) {
        Set<Question> questionSet = new HashSet<>();
        Long questionCountByType = countAllByType(type);
        int index = (int) (Math.random() * questionCountByType);
        Page<Question> questionPage = questionRepository.findAllByType(type, PageRequest.of(index,1));
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
