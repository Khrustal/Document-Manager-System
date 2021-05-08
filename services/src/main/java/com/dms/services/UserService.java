package com.dms.services;

import com.dms.model.Storable;
import com.dms.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    public Optional<User> find(String username);
    public Optional<User> find(Long id);
    public Boolean checkReader(Long userId, Long storableId);
    public Boolean checkEditor(Long userId, Long storableId);
    public Boolean checkModerator(Long userId, Long storableId);
    public List<Storable> getModerationList(Long id);
}
