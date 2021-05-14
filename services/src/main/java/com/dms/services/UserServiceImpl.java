package com.dms.services;

import com.dms.dao.UserRepository;
import com.dms.model.Storable;
import com.dms.model.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    UserRepository userRepository;
    StorableService storableService;

    public UserServiceImpl(UserRepository userRepository, StorableService storableService) {
        this.userRepository = userRepository;
        this.storableService = storableService;
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
    public User getCurrent() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userRepository.findUserByUsername(userDetails.getUsername()).orElseThrow(RuntimeException::new);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public boolean checkGrant(Storable storable, User user, String right) {
        User current = getCurrent();
        boolean canGrant = false;

        //Check if current can grant
        if(right.equals("moderator") || right.equals("editor")) {
            canGrant = storable.getModerators().contains(current);
        }
        else if(right.equals("reader")) {
            canGrant = storable.getEditors().contains(current) || storable.getModerators().contains(current);
        }

        //Check user rights
        //moderator -> error
        //editor and already editor -> error
        //reader and already reader -> error
        boolean wrongRightAssigment = storable.getModerators().contains(user) ||
                (right.equals("editor") && storable.getEditors().contains(user)) ||
                (right.equals("reader") && storable.getReaders().contains(user));

        return (canGrant && !wrongRightAssigment);
    }
}
