package com.dms.dto;

import com.dms.model.*;

import java.sql.Timestamp;
import java.util.List;

public class DocumentDto {
    private Long id;
    private UserDto author;
    private String name;
    private Type type;
    private Status status;
    private Timestamp creation_DT;
    private boolean freeAccess;
    private String description;

    private Priority priority;

    private DocType docType;

    private DocumentDto ancestor;

    private List<DocFile> files;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserDto getAuthor() {
        return author;
    }

    public void setAuthor(UserDto author) {
        this.author = author;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Status getStatus() {
        return status;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Timestamp getCreation_DT() {
        return creation_DT;
    }

    public void setCreation_DT(Timestamp creation_DT) {
        this.creation_DT = creation_DT;
    }

    public boolean isFreeAccess() {
        return freeAccess;
    }

    public void setFreeAccess(boolean freeAccess) {
        this.freeAccess = freeAccess;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public DocType getDocType() {
        return docType;
    }

    public void setDocType(DocType docType) {
        this.docType = docType;
    }

    public DocumentDto getAncestor() {
        return ancestor;
    }

    public void setAncestor(DocumentDto ancestor) {
        this.ancestor = ancestor;
    }

    public List<DocFile> getFiles() {
        return files;
    }

    public void setFiles(List<DocFile> files) {
        this.files = files;
    }
}
