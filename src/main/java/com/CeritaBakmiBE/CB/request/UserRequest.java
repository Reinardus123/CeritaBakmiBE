package com.CeritaBakmiBE.CB.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserRequest {

    @NotBlank(message = "Username harus diisi")
    @Size(min = 5, max = 30, message = "Username minimal 5 karakter")
    private String username;

    @NotBlank(message = "Email harus diisi")
    @Email(message = "Format email salah")
    private String email;

    @NotBlank(message = "Password harus diisi")
    @Size(min = 5, max = 30, message = "Password harus 5-30 karakter")
    private String password;
}
