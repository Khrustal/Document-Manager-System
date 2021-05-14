package com.dms.services.mappers;

import com.dms.dto.DirectoryDto;
import com.dms.model.Directory;
import com.dms.model.Storable;

public class DirectoryDtoMapper {

    public static DirectoryDto map(Directory directory) {
        DirectoryDto dto = new DirectoryDto();
        dto.setId(directory.getId());
        dto.setAuthor(UserDtoMapper.map(directory.getAuthor()));
        dto.setName(directory.getName());
        dto.setType(directory.getType());
        dto.setStatus(directory.getStatus());
        dto.setCreation_DT(directory.getCreationDT());
        dto.setFreeAccess(directory.getFreeAccess());
        return dto;
    }

    public static DirectoryDto map(Storable storable) {
        DirectoryDto dto = new DirectoryDto();
        dto.setId(storable.getId());
        dto.setAuthor(UserDtoMapper.map(storable.getAuthor()));
        dto.setName(storable.getName());
        dto.setType(storable.getType());
        dto.setStatus(storable.getStatus());
        dto.setCreation_DT(storable.getCreationDT());
        dto.setFreeAccess(storable.getFreeAccess());
        return dto;
    }
}
