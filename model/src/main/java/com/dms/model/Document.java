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

    public Document(Long id, Directory parent, User author, String name, Type type, Boolean freeAccess, Status status,
                    Timestamp creationDT, String description, Priority priority, DocType docType, Document ancestor) {
        super(id, parent, author, name, type, freeAccess, status, creationDT);
        this.description = description;
        this.priority = priority;
        this.docType = docType;
        this.ancestor = ancestor;
    }

    public Document() {

    }
}
