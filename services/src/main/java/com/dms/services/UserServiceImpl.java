package com.dms.services;

import com.dms.dao.UserRepository;
import com.dms.model.Storable;
import com.dms.model.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<User> find(String username) {
        return userRepository.findUserByUsername(username);
    }

    @Override
    public Optional<User> find(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public Boolean checkReader(Long userId, Long storableId) {
        return null;
    }

    @Override
    public Boolean checkEditor(Long userId, Long storableId) {
        return null;
    }

    @Override
    public Boolean checkModerator(Long userId, Long storableId) {
        return null;
    }

    @Override
    public List<Storable> getModerationList(Long id) {
        return null;
    }
}
