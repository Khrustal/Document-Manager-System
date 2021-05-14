package com.dms.services;

import com.dms.model.Document;

import java.util.List;
import java.util.Optional;

public interface DocumentService {
    public void create(Document document);
    public Document find(Long id);
    public List<Document> find(String name);
    public void delete(Long id);
    public Document fundByFileId(Long id);
    public Optional<Document> findModerated(Document ancestor);
    public Optional<Document> findPrevModerated(Document document);
}
