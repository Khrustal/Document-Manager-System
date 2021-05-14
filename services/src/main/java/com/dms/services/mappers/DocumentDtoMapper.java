package com.dms.services.mappers;

import com.dms.dto.DocumentDto;
import com.dms.model.Document;

public class DocumentDtoMapper {
    public static DocumentDto map(Document document) {
        DocumentDto dto = new DocumentDto();
        dto.setId(document.getId());
        dto.setAuthor(UserDtoMapper.map(document.getAuthor()));
        dto.setName(document.getName());
        dto.setType(document.getType());
        dto.setStatus(document.getStatus());
        dto.setCreation_DT(document.getCreationDT());
        dto.setFreeAccess(document.getFreeAccess());
        dto.setDescription(document.getDescription());
        dto.setPriority(document.getPriority());
        dto.setDocType(document.getDocType());
        dto.setAncestor(DocumentDtoMapper.map(document.getAncestor()));
        dto.setFiles(document.getFiles());
        return dto;
    }
}
