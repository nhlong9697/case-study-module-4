package com.quiz.casestudy.model;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Table
@Data
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty
    @Column(nullable = false)
    private String name;

    @ManyToOne
    private Classes classId;

    private String avatar;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    private AppUser appUser;

    @Transient
    private MultipartFile image;

    public Student() {
    }

    public Student(String name, MultipartFile multipartFile) {
        this.name = name;
        this.image = multipartFile;
    }

    public Student(String name, String avatar) {
        this.name = name;
        this.avatar = avatar;
    }

}
