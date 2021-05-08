package com.dms.services;

import com.dms.model.DocType;

import java.util.Optional;

public interface DocTypeService {
    public void create(DocType docType);
    public Optional<DocType> find(Long id);
}
