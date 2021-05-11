package com.dms.dao;

import com.dms.model.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {
    List<Document> findByParentId(Long id);
    Document findByFilesId(Long id);
    List<Document> findByName(String name);
    List<Document> findByNameContains(String name);
}
