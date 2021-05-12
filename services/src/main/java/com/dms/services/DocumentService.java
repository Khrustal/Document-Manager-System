package com.dms.services;

import com.dms.model.Document;
import com.dms.model.User;

import java.util.List;

public interface DocumentService {
    public void create(Document document);
    public Document find(Long id);
    public List<Document> find(String name);
    public void delete(Long id);
    public Document fundByFileId(Long id);
}
