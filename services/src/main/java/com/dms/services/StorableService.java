package com.dms.services;

import com.dms.dao.StorableRepository;
import com.dms.model.Storable;
import com.dms.model.User;

import java.util.List;

public interface StorableService {

    public void save(Storable storable);
    public Storable find(Long id);
    public boolean canDelete(Long id, User user);
    public boolean canEdit(Long id, User user);
    public List<Storable> getModerationList(User user);
    public boolean isFreeAccess(Long id);
}
