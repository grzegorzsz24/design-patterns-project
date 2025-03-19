package com.example.automotiveapp.auth;


import com.example.automotiveapp.dto.ValidationConstants;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    @NotBlank
    @Size(min = 2, max = ValidationConstants.MAX_NAME_LENGTH)
    private String firstName;
    @NotBlank
    @Size(min = 2, max = ValidationConstants.MAX_SURNAME_LENGTH)
    private String lastName;
    @NotBlank
    @Size(min = 2, max = ValidationConstants.MAX_NICKNAME_LENGTH)
    private String nickname;
    @Email
    private String email;
    @NotBlank
    @Size(min = ValidationConstants.MIN_PASSWORD_LENGTH)
    private String password;
    @NotNull
    private Date dateOfBirth;
}
