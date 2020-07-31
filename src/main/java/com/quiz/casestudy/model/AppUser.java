package com.quiz.casestudy.model;


import com.quiz.casestudy.validator.ValidEmail;
import com.quiz.casestudy.validator.ValidPassword;
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

    @Column(nullable = false,unique = true,length = 200)
    @ValidEmail(message = "{user.register.error.invalidEmail}")
    private String email;

    @Column(nullable = false)
    @ValidPassword(message = "{user.register.error.invalidPassword}")
    private String password;

    @ManyToOne
    private AppRole role;
}
