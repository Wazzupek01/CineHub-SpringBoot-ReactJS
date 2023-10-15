package com.pedrycz.cinehub.validation;

import com.pedrycz.cinehub.repositories.UserRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.NoSuchElementException;

public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {

    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        try {
            userRepository.findUserByEmail(s).get();
        } catch(NoSuchElementException e){
            return true;
        }
        return false;
    }
}
