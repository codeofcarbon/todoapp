package com.codeofcarbon.todoapp.entities.dto;

import com.codeofcarbon.todoapp.entities.*;
import lombok.*;

import java.util.List;

@Getter
@Builder
public class UserDTO {
    private final long id;
    private final String name;
    private final String lastname;
    private final String email;
    private final List<Role> roles;

    public static UserDTO mapToUserDTO(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .name(user.getUsername())
                .build();
    }
}