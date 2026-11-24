package com.openclassrooms.etudiant.mapper;

import com.openclassrooms.etudiant.dto.CreateUserRequestDTO;
import com.openclassrooms.etudiant.dto.UserLightDTO;
import com.openclassrooms.etudiant.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface UserDtoMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "created_at", ignore = true)
    @Mapping(target = "updated_at", ignore = true)
    @Mapping(target = "authorities", ignore = true)
    User toEntity(CreateUserRequestDTO registerDTO);
    UserLightDTO toUserLightDTO(User user);
}
