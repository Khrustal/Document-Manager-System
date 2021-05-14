package com.dms.dao;

import com.dms.model.Document;
import com.dms.model.Status;
import com.dms.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {
    List<Document> findByParentId(Long id);
    Document findByFilesId(Long id);
    List<Document> findByName(String name);
    List<Document> findByNameContains(String name);
    Optional<Document> findByStatusAndAncestor(Status status, Document document);
}
