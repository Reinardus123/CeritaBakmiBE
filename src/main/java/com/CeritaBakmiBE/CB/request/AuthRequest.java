package com.CeritaBakmiBE.CB.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AuthRequest {

    @NotBlank(message = "username tidak boleh kosong")
    @Size(min = 5, max = 30, message = "Username minimal 5 karakter")
    private String username;

    @NotBlank(message = "Password tidak boleh kosong")
    @Size(min = 5, max = 30, message = "Password minimal 5 karakter")
    private String password;
}
