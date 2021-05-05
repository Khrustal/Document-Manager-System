package com.dms.model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "storable")
public class Storable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private Storable parent;

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

    @OneToMany
    private List<User> readers;

    @OneToMany
    private List<User> editors;

    @OneToMany
    private List<User> moderators;

    private String description;

    @Enumerated(EnumType.ORDINAL)
    private Priority priority;

    @ManyToOne
    private DocType docType;

    @ManyToOne
    private Storable ancestor;

    public Long getId() {
        return id;
    }

    public Storable getParent() {
        return parent;
    }

    public void setParent(Storable parent) {
        this.parent = parent;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
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

    public void setReaders(List<User> readers) {
        this.readers = readers;
    }

    public List<User> getEditors() {
        return editors;
    }

    public void setEditors(List<User> editors) {
        this.editors = editors;
    }

    public List<User> getModerators() {
        return moderators;
    }

    public void setModerators(List<User> moderators) {
        this.moderators = moderators;
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

    public Storable getAncestor() {
        return ancestor;
    }

    public void setAncestor(Storable ancestor) {
        this.ancestor = ancestor;
    }
}
