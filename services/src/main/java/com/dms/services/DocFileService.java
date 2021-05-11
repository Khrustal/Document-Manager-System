package com.dms.services;

import com.dms.model.DocFile;

import java.util.List;

public interface DocFileService {
    public void save(DocFile docFile);
    public List<DocFile> findAll();
    public DocFile find(Long id);
    public void delete(Long id);
    public void delete(DocFile docFile);
}
