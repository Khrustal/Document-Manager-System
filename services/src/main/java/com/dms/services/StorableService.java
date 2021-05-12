package com.dms.services;

import com.dms.dao.StorableRepository;
import com.dms.model.Storable;

public interface StorableService {

    public void save(Storable storable);
    public Storable find(Long id);

}
