package com.example.automotiveapp.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
public class UserDto {
    private Long id;
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
    @NotNull
    private Date dateOfBirth;
    @NotBlank
    @Size(min = ValidationConstants.MIN_PASSWORD_LENGTH)
    private String password;
    private boolean publicProfile;
    private String imageUrl;
}
