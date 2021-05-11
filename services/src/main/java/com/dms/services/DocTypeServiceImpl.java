package com.dms.services;

import com.dms.dao.DocTypeRepository;
import com.dms.model.DocType;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DocTypeServiceImpl implements DocTypeService{

    DocTypeRepository docTypeRepository;

    public DocTypeServiceImpl(DocTypeRepository docTypeRepository) {
        this.docTypeRepository = docTypeRepository;
    }

    @Override
    public void create(DocType docType) {
        docTypeRepository.save(docType);
    }

    @Override
    public Optional<DocType> find(Long id) {
        return docTypeRepository.findById(id);
    }

    @Override
    public List<DocType> findAll() {
        return docTypeRepository.findAll();
    }


}
