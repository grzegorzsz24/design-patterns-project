package com.example.automotiveapp.auth;

import com.example.automotiveapp.dto.ValidationConstants;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequest {
    @Email
    private String email;
    @NotBlank
    @Size(min = ValidationConstants.MIN_PASSWORD_LENGTH)
    private String password;
}
