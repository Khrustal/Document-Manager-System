package com.dms.services;

import com.dms.model.DocType;

import java.util.List;
import java.util.Optional;

public interface DocTypeService {
    public void create(DocType docType);
    public DocType find(Long id);
    public List<DocType> findAll();
    public void delete(Long id);
}
