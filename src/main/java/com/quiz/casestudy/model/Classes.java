package com.quiz.casestudy.model;

import com.quiz.casestudy.validator.UniqueClassesName;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Table
@Data
public class Classes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotEmpty(message = "{class.register.error.notEmpty}")
    @UniqueClassesName(message = "{classes.register.error.duplicateClasses}")
    private String name;

    @ManyToOne
    private Program program;
}
