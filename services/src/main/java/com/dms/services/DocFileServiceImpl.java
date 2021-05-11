package com.dms.services;

import com.dms.dao.DocFileRepository;
import com.dms.model.DocFile;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DocFileServiceImpl implements DocFileService{

    DocFileRepository repo;

    public DocFileServiceImpl(DocFileRepository repo) {
        this.repo = repo;
    }

    @Override
    public void save(DocFile docFile) {
        repo.save(docFile);
    }

    @Override
    public List<DocFile> findAll() {
        return repo.findAll();
    }

    @Override
    public DocFile find(Long id) {
        return repo.findById(id).get();
    }

    @Override
    public void delete(Long id) {
        repo.deleteById(id);
    }

    @Override
    public void delete(DocFile docFile) {
        repo.delete(docFile);
    }
}
