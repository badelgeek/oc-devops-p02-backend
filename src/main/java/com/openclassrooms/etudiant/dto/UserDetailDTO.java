package com.openclassrooms.etudiant.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class UserDetailDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String login;
    private LocalDateTime createdAt;
}
