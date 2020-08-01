package com.quiz.casestudy.model;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Entity
@Table(uniqueConstraints={@UniqueConstraint(columnNames={"authority"})})
@Data
public class AppRole implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    @NotEmpty
    private String authority;

    @Override
    public String getAuthority() {
        return this.authority;
    }

    public AppRole() {
    }
    public AppRole(String authority) {
        this.authority = authority;
    }
}
