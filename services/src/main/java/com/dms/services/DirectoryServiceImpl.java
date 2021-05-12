package com.dms.services;

import com.dms.dao.DirectoryRepository;
import com.dms.dao.DocumentRepository;
import com.dms.model.Directory;
import com.dms.model.Document;
import com.dms.model.Storable;
import com.dms.model.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DirectoryServiceImpl implements DirectoryService{

    DirectoryRepository directoryRepository;
    DocumentRepository documentRepository;

    public DirectoryServiceImpl(DirectoryRepository directoryRepository, DocumentRepository documentRepository) {
        this.directoryRepository = directoryRepository;
        this.documentRepository = documentRepository;
    }

    @Override
    public void create(Directory directory) {
        directoryRepository.save(directory);
    }

    @Override
    public Optional<Directory> find(Long id) {
        return directoryRepository.findById(id);
    }

    @Override
    public List<Directory> find(String name) {
        return directoryRepository.findByNameContains(name);
    }

    @Override
    public List<Directory> findAll() {
        return directoryRepository.findAll();
    }

    @Override
    public List<Storable> getContent(Long id) {
        List<Directory> directories = directoryRepository.findByParentId(id);
        List<Document> documents = documentRepository.findByParentId(id);
        List<Storable> result = new ArrayList<Storable>(directories);
        result.addAll(documents);
        return result;
    }

    @Override
    public List<Storable> getContents(Long id) {
        return null;
    }

    @Override
    public void delete(Long id) {
        directoryRepository.deleteById(id);
    }
}
