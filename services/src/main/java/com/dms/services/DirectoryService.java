package com.dms.services;

import com.dms.model.Directory;
import com.dms.model.Storable;

import java.util.List;

public interface DirectoryService {
    public void create(Directory directory);
    public Directory find(Long id);
    public void update(Long id, Directory newDir);
    public List<Storable> getContents(Long id);
    public void delete(Long id);
}
