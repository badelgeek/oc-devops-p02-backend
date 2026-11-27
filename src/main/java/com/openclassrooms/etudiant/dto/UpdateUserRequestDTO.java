package com.openclassrooms.etudiant.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateUserRequestDTO {
    @NotBlank 
    private String login;
    @NotBlank 
    private String firstName;
    @NotBlank 
    private String lastName;

    private String password;
}
