package com.quiz.casestudy.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ClassNameValidator
        implements ConstraintValidator<ValidClassesName, String> {

    private Pattern pattern;
    private Matcher matcher;
    private static final String CLASSESNAME_PATTERN = "^[CAP](0[1-9]|1[0-2])\\d{2}[GHIKML]$";

    @Override
    public void initialize(ValidClassesName constraintAnnotation) {
    }

    @Override
    public boolean isValid(String classesName, ConstraintValidatorContext context) {
        return (validateEmail(classesName));
    }

    private boolean validateEmail(String email) {
        pattern = Pattern.compile(CLASSESNAME_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }
}