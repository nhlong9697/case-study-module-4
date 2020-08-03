package com.quiz.casestudy.validator;

import com.quiz.casestudy.service.classes.IClassesService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueClassesNameValidator implements ConstraintValidator<UniqueClassesName,String> {
    @Autowired
    private IClassesService classesService;
    @Override
    public void initialize(UniqueClassesName constraintAnnotation) {
    }
    @Override
    public boolean isValid(String name, ConstraintValidatorContext context) {
        return classesService == null || !classesService.existsByName(name);
    }
}
