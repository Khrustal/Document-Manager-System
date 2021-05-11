package com.dms.model;

import javax.persistence.Entity;
import java.sql.Timestamp;

@Entity
public class Directory extends Storable{

    public Directory(Long id, Directory parent, User author, String name, Type type, Boolean freeAccess, Status status, Timestamp creationDT) {
        super(id, parent, author, name, type, freeAccess, status, creationDT);
    }

    public Directory() {

    }

}
