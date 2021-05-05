package com.dms.dao;

import com.dms.model.DocFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocFileRepository extends JpaRepository<DocFile, Long> {
}
