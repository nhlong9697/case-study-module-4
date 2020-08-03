package com.quiz.casestudy.model;

import lombok.Data;

import javax.persistence.*;

@Table
@Entity
@Data
public class QuizAssignment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Quiz quiz;

    @ManyToOne
    private Module module;

    @ManyToOne
    private Classes classes;
}
