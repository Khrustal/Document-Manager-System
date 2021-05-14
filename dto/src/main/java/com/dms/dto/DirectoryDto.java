package com.dms.dto;

import com.dms.model.Status;
import com.dms.model.Type;

import java.sql.Timestamp;

public class DirectoryDto {
    private Long id;
    private UserDto author;
    private String name;
    private Type type;
    private Status status;
    private Timestamp creation_DT;
    private boolean freeAccess;

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

    public void setStatus(Status status) {
        this.status = status;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
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
}
