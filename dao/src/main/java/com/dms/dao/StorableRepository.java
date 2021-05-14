package com.dms.dao;

import com.dms.model.Status;
import com.dms.model.Storable;
import com.dms.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StorableRepository extends JpaRepository<Storable, Long> {
    List<Storable> findByStatusAndModeratorsContaining(Status status, User user);
}
