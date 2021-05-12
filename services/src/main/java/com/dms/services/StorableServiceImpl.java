package com.dms.services;

import com.dms.dao.StorableRepository;
import com.dms.model.Storable;
import org.springframework.stereotype.Service;

@Service
public class StorableServiceImpl implements StorableService{

    StorableRepository repo;

    public StorableServiceImpl(StorableRepository repo) {
        this.repo = repo;
    }

    @Override
    public void save(Storable storable) {
        repo.save(storable);
    }

    @Override
    public Storable find(Long id) {
        return repo.findById(id).orElseThrow(RuntimeException::new);
    }
}
