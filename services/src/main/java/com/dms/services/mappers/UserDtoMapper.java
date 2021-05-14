package com.dms.services.mappers;

import com.dms.dto.UserDto;
import com.dms.model.User;

public class UserDtoMapper {
    public static UserDto map(User user) {
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setAdmin(user.isAdmin());
        return dto;
    }
}
