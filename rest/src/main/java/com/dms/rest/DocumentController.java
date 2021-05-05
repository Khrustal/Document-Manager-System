package com.dms.rest;

import com.dms.dao.StorableRepository;
import com.dms.model.Storable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/doc")
public class DocumentController {

    StorableRepository storableRepository;

    public DocumentController(StorableRepository storableRepository) {
        this.storableRepository = storableRepository;
    }
}
