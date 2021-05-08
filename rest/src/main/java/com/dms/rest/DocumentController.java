package com.dms.rest;

import com.dms.dao.DocumentRepository;
import com.dms.model.*;
import com.dms.services.DocTypeService;
import com.dms.services.DocumentService;
import com.dms.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.Optional;

@RestController
@RequestMapping("/doc")
public class DocumentController {

    DocumentService documentService;
    UserService userService;
    DocTypeService docTypeService;

    public DocumentController(DocumentService documentService, UserService userService, DocTypeService docTypeService) {
        this.documentService = documentService;
        this.userService = userService;
        this.docTypeService = docTypeService;
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        return "create_doc";
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(Model model) {
        Optional<User> user = userService.find(1L);
        Optional<DocType> docType = docTypeService.find(1L);
        Document document = new Document(null, null, user.get(), "example-doc", Type.DOCUMENT,
                true, Status.CURRENT, new Timestamp(System.currentTimeMillis()), "description", Priority.MEDIUM,
                docType.get(), null);
        documentService.create(document);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
