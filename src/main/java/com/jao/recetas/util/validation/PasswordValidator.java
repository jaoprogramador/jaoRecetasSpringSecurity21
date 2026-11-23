package com.jao.recetas.util.validation;

import com.jao.recetas.dto.UsuarioRequest;
import com.jao.recetas.services.security.data.NewPasswordInfo;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<ValidPassword, UsuarioRequest> {
    @Override
    public boolean isValid(UsuarioRequest usuarioRequest, ConstraintValidatorContext constraintValidatorContext) {
        if (usuarioRequest == null) {
            return false;
        }

        return usuarioRequest.getPassword().equals(usuarioRequest.getConfirmacion());
    }
}