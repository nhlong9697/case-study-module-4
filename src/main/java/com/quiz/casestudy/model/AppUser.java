package com.quiz.casestudy.model;


import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@Table(uniqueConstraints={@UniqueConstraint(columnNames={"email"})})
@Data
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,length = 200)
    @Pattern(regexp = "[^@ \\t\\r\\n]+@[^@ \\t\\r\\n]+\\.[^@ \\t\\r\\n]+", message = "{invalid.email}")
    private String email;

    @Column(nullable = false)
    @Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$ %^&*-]).{8,}$", message = "{invalid.password}")
    private String password;

    @ManyToOne
    private AppRole role;
}
