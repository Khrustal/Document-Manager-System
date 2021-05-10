package com.dms.dto;

public class CreateDirDto {
    public String name;
    //public Long parent;
    public Boolean freeAccess;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

//    public Long getParent() {
//        return parent;
//    }
//
//    public void setParent(Long parent) {
//        this.parent = parent;
//    }

    public Boolean getFreeAccess() {
        return freeAccess;
    }

    public void setFreeAccess(Boolean freeAccess) {
        this.freeAccess = freeAccess;
    }
}
