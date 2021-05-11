package com.dms.services;

import com.dms.dao.DocumentRepository;
import com.dms.model.Document;
import org.springframework.stereotype.Service;

@Service
public class DocumentServiceImpl implements DocumentService{

    DocumentRepository documentRepository;

    public DocumentServiceImpl(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }

    @Override
    public void create(Document document) {
        documentRepository.save(document);
    }

    @Override
    public Document find(Long id) {
        return documentRepository.findById(id).get();
    }

    @Override
    public void update(Long id, Document newDoc) {
    }

    @Override
    public void delete(Long id) {
        documentRepository.deleteById(id);
    }

    @Override
    public Document fundByFileId(Long id) {
        return documentRepository.findByFilesId(id);
    }
}
