package com.jao.recetas.services.security.data;

import com.jao.recetas.util.validation.ValidNewPassword;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
@ValidNewPassword
public class NewPasswordInfo {
    @NotBlank(message = "La contraseña actual del usuario es obligatoria")
    private String password;

    @NotBlank(message = "La contraseña nueva del usuario es obligatoria")
    private String newPassword;
    private String confirmation;
}