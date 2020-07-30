package com.quiz.casestudy.model;

import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;

@Entity
@Table
public class Program {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String avatar;

    @Transient
    private MultipartFile image;

    public Program() {
    }

    public Program(String name,MultipartFile multipartFile){
        this.name = name;
        this.image= multipartFile;
    }

    public Program(String name,String avatar){
        this.name = name;
        this.avatar = avatar;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }

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
}
