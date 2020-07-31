package com.quiz.casestudy.model;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Table
@Data
public class Classes {
    @Autowired
    private MessageSource messageSource;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotEmpty(message = "{class.register.error.notEmpty}")
    @Column(nullable = false)
    private String name;

    @ManyToOne
    private Program programId;
}
