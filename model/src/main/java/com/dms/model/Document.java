package com.dms.model;

import javax.persistence.*;

@Entity
public class Document extends Storable{

    private String description;

    @Enumerated(EnumType.ORDINAL)
    private Priority priority;

    @ManyToOne
    private DocType docType;

    @ManyToOne
    private Document ancestor;
}
