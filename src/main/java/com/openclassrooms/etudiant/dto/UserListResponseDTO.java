package com.openclassrooms.etudiant.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserListResponseDTO {
    private List<UserLightDTO> users;
}
