package com.dms.dao;

import com.dms.model.Directory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DirectoryRepository extends JpaRepository<Directory, Long> {
    List<Directory> findByParentId(Long id);
    List<Directory> findByName(String name);
    List<Directory> findByNameContains(String name);
}
