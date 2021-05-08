package com.dms.services;

import com.dms.dao.DirectoryRepository;
import com.dms.model.Directory;
import com.dms.model.Storable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DirectoryServiceImpl implements DirectoryService{

    DirectoryRepository repo;

    public DirectoryServiceImpl(DirectoryRepository repo) {
        this.repo = repo;
    }

    @Override
    public void create(Directory directory) {
        repo.save(directory);
    }

    @Override
    public Directory find(Long id) {
        return null;
    }

    @Override
    public void update(Long id, Directory newDir) {

    }

    @Override
    public List<Storable> getContents(Long id) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
