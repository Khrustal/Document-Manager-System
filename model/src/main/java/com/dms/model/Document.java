package com.dms.model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity
public class Document extends Storable{

    private String description;

    @Enumerated(EnumType.ORDINAL)
    private Priority priority;

    @ManyToOne
    private DocType docType;

    @ManyToOne
    private Document ancestor;

    @OneToMany
    private List<DocFile> files;

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

    public String getDescription() {
        return description;
    }

    public Priority getPriority() {
        return priority;
    }

    public DocType getDocType() {
        return docType;
    }

    public Document getAncestor() {
        return ancestor;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public void setDocType(DocType docType) {
        this.docType = docType;
    }

    public void setAncestor(Document ancestor) {
        this.ancestor = ancestor;
    }

    public List<DocFile> getFiles() {
        return files;
    }

    public void addFile(DocFile file) {
        this.files.add(file);
    }

    public void removeFile(DocFile docFile) {
        this.files.remove(docFile);
    }
}
