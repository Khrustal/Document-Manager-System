package com.dms.dao;

import com.dms.model.DocType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocTypeRepository extends JpaRepository<DocType, Long> {
}
