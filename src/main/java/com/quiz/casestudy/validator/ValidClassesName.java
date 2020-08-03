package com.quiz.casestudy.validator;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({TYPE, FIELD, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = {ClassNameValidator.class})
@Documented
public @interface ValidClassesName {
    String message() default "Invalid class name";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
