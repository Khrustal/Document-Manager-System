package com.dms.model;

import javax.persistence.*;

@Entity
@Table(name = "docfile")
public class DocFile {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private String type;

    private String path;

    public DocFile(String name, String type, String path) {
        this.name = name;
        this.type = type;
        this.path = path;
    }

    public DocFile() {
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
