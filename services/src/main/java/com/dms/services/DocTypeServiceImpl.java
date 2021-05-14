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
    public DocType find(Long id) {
        return docTypeRepository.findById(id).get();
    }

    @Override
    public List<DocType> findAll() {
        return docTypeRepository.findAll();
    }

    @Override
    public void delete(Long id) {
        docTypeRepository.deleteById(id);
    }


}
