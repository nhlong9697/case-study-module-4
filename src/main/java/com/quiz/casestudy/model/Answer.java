package com.quiz.casestudy.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private boolean status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id", referencedColumnName = "id")
    @JsonIgnore
    private Question question;

    @ManyToMany
    @JoinTable(name = "student_quiz_answer" , joinColumns = @JoinColumn(name =
            "answer_id"), inverseJoinColumns = @JoinColumn(name = "student_quiz_id"))
    private Set<StudentQuiz> studentQuizs;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public Set<StudentQuiz> getStudentQuizs() {
        return studentQuizs;
    }

    public void setStudentQuizs(Set<StudentQuiz> studentQuizs) {
        this.studentQuizs = studentQuizs;
    }
}
