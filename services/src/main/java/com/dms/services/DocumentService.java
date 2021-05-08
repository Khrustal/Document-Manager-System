package com.dms.services;

import com.dms.model.Document;

public interface DocumentService {
    public void create(Document document);
    public Document find(Long id);
    public void update(Long id, Document newDoc);
    public void delete(Long id);
}
