package com.dms.services;

import com.dms.dao.StorableRepository;
import com.dms.model.Storable;
import com.dms.model.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

    @Override
    public boolean canDelete(Long id, User user) {
        return find(id).getModerators().contains(user);
    }

    @Override
    public boolean canEdit(Long id, User user) {
        Storable storable = find(id);
        return (storable.getModerators().contains(user) || storable.getEditors().contains(user));
    }
}
