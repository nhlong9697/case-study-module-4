package com.quiz.casestudy.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.quiz.casestudy.validator.UniqueEmail;
import com.quiz.casestudy.validator.ValidEmail;
import com.quiz.casestudy.validator.ValidPassword;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@Table
@Data
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,length = 200)
    @ValidEmail(message = "{user.register.error.invalidEmail}")
    @UniqueEmail(message = "{user.register.error.duplicateEmail}")
    private String email;

    @Column(nullable = false)
    @ValidPassword(message = "{user.register.error.invalidPassword}")
    private String password;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private AppRole role;
}
