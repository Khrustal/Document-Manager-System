package com.dms.model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;


@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Storable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private Directory parent;

    @ManyToOne
    private User author;

    private String name;

    @Enumerated(EnumType.STRING)
    private Type type;

    @Column(name = "free_access")
    private Boolean freeAccess;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "creation_dt")
    private Timestamp creationDT;

    @ManyToMany
    private final List<User> readers = new ArrayList<>();

    @ManyToMany
    private final List<User> editors = new ArrayList<>();

    @ManyToMany
    private final List<User> moderators = new ArrayList<>();

    public Storable(Long id, Directory parent, User author, String name, Type type, Boolean freeAccess, Status status, Timestamp creationDT) {
        this.id = id;
        this.parent = parent;
        this.author = author;
        this.name = name;
        this.type = type;
        this.freeAccess = freeAccess;
        this.status = status;
        this.creationDT = creationDT;
    }

    public Storable() {
    }

    public Long getId() {
        return id;
    }

    public Directory getParent() {
        return parent;
    }

    public void setParent(Directory parent) {
        this.parent = parent;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getFreeAccess() {
        return freeAccess;
    }

    public void setFreeAccess(Boolean freeAccess) {
        this.freeAccess = freeAccess;
    }

    public Status getStatus() {
        return status;
    }

    public Type getType() {
        return type;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Timestamp getCreationDT() {
        return creationDT;
    }

    public void setCreationDT(Timestamp creationDT) {
        this.creationDT = creationDT;
    }

    public List<User> getReaders() {
        return readers;
    }

    public void addReader(User user) {
        this.readers.add(user);
    }

    public void addReaders(List<User> user) {
        this.readers.addAll(user);
    }

    public List<User> getEditors() {
        return editors;
    }

    public void addEditor(User user) {
        this.editors.add(user);
    }

    public void addEditors(List<User> user) {
        this.editors.addAll(user);
    }

    public List<User> getModerators() {
        return moderators;
    }

    public void addModerator(User user) {
        this.moderators.add(user);
    }

    public void addModerators(List<User> user) {
        this.moderators.addAll(user);
    }
}
