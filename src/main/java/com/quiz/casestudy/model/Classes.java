package com.quiz.casestudy.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.quiz.casestudy.validator.UniqueClassesName;
import com.quiz.casestudy.validator.ValidClassesName;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Entity
@Table
public class Classes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotEmpty(message = "{class.register.error.notEmpty}")
    @ValidClassesName(message = "{classes.register.error.invalidClassesName}")
    @UniqueClassesName(message = "{classes.register.error.duplicateClasses}")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Program program;

    @OneToMany(mappedBy = "classes")
    private Set<Student> students;

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

    public Program getProgram() {
        return program;
    }

    public void setProgram(Program program) {
        this.program = program;
    }

    public Set<Student> getStudents() {
        return students;
    }

    public void setStudents(Set<Student> students) {
        this.students = students;
    }
}
