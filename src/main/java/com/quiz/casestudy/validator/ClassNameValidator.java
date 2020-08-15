package com.quiz.casestudy.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ClassNameValidator
        implements ConstraintValidator<ValidClassesName, String> {

    private static final String CLASSES_NAME_PATTERN = "^[CAP](0[1-9]|1[0-2])" +
            "\\d{2}[GHIKML][1-9]$";

    @Override
    public void initialize(ValidClassesName constraintAnnotation) {
    }

    @Override
    public boolean isValid(String classesName, ConstraintValidatorContext context) {
        return (validateEmail(classesName));
    }

    private boolean validateEmail(String email) {
        Pattern pattern = Pattern.compile(CLASSES_NAME_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}