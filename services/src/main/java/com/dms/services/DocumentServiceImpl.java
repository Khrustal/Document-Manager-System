package com.dms.services;

import com.dms.dao.DocumentRepository;
import com.dms.model.Document;
import com.dms.model.Status;
import com.dms.model.Storable;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class DocumentServiceImpl implements DocumentService{

    DocumentRepository documentRepository;
    UserService userService;

    public DocumentServiceImpl(DocumentRepository documentRepository, UserService userService) {
        this.documentRepository = documentRepository;
        this.userService = userService;
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
    public List<Document> find(String name) {
        return documentRepository.findByNameContains(name);
    }

    @Override
    public void delete(Long id) {
        documentRepository.deleteById(id);
    }

    @Override
    public Document fundByFileId(Long id) {
        return documentRepository.findByFilesId(id);
    }

    @Override
    public Optional<Document> findModerated(Document ancestor) {
        return documentRepository.findByStatusAndAncestor(Status.ON_MODERATION, ancestor);
    }

    @Override
    public Optional<Document> findPrevModerated(Document document) {
        return documentRepository.findByStatusAndAncestor(Status.CURRENT, document.getAncestor());
    }

    @Override
    public Optional<Document> findLatestOld(Document document) {
        List<Document> documents = documentRepository.findByStatus(Status.OLD);
        documents.sort(Comparator.comparing(Storable::getCreationDT).reversed());

        Optional<Document> result = Optional.empty();

        if(!documents.isEmpty()) {
            result = Optional.ofNullable(documents.get(0));
        }

        return result;
    }

    @Override
    public List<Document> findByAncestor(Document document) {
        return documentRepository.findByAncestor(document);
    }
}
