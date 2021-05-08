package com.dms.model;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
public class Document extends Storable{

    private String description;

    @Enumerated(EnumType.ORDINAL)
    private Priority priority;

    @ManyToOne
    private DocType docType;

    @ManyToOne
    private Document ancestor;

    public Document(Long id, Directory parent, User author, String name, Type type, Boolean freeAccess, Status status, Timestamp creationDT) {
        super(id, parent, author, name, type, freeAccess, status, creationDT);
    }

    public Document() {

    }
}
