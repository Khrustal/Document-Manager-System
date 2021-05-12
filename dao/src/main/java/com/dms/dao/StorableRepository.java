package com.dms.dao;

import com.dms.model.Storable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StorableRepository extends JpaRepository<Storable, Long> {
}
