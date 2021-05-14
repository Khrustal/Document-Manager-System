package com.dms.model;

import javax.persistence.*;

@Entity
@Table(name = "doctype")
public class DocType {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    private String name;

    public DocType(String name) {
        this.name = name;
    }

    public DocType() {
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
}
