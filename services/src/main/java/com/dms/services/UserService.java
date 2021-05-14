package com.dms.services;

import com.dms.model.Storable;
import com.dms.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    public Optional<User> find(String username);
    public Optional<User> find(Long id);
    public User getCurrent();
    public List<User> findAll();
    public boolean checkGrant(Storable storable, User user, String right);
}
