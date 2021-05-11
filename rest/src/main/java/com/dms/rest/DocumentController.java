package com.dms.rest;

import com.dms.dto.CreateDirDto;
import com.dms.dto.CreateDocDto;
import com.dms.model.*;
import com.dms.services.DirectoryService;
import com.dms.services.DocTypeService;
import com.dms.services.DocumentService;
import com.dms.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/doc")
public class DocumentController {

    DocumentService documentService;
    DirectoryService directoryService;
    UserService userService;
    DocTypeService docTypeService;

    public DocumentController(DocumentService documentService, DirectoryService directoryService,
                              UserService userService, DocTypeService docTypeService) {
        this.documentService = documentService;
        this.directoryService = directoryService;
        this.userService = userService;
        this.docTypeService = docTypeService;
    }

    @GetMapping("/create")
    public String createForm(@RequestParam(required = false) Long id, Model model) {
        model.addAttribute("parentId", id);
        model.addAttribute("document", new CreateDocDto());
        List<DocType> docTypeList = docTypeService.findAll();
        model.addAttribute("docTypes", docTypeList);
        return "create_doc";
    }

    @PostMapping("/create")
    public String create(@RequestParam(required = false) Long id, @ModelAttribute CreateDocDto dto, Model model) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.find(userDetails.getUsername()).get();
        Directory parent = null;
        if(id != null) {
            parent = directoryService.find(id).get();
        }
        Document document = new Document(null, parent, user, dto.getName(), Type.DOCUMENT,
                dto.getFreeAccess(), Status.CURRENT, new Timestamp(System.currentTimeMillis()),
                dto.getDescription(), dto.getPriority(), dto.getDocType(), null);
        documentService.create(document);
        return "home";
    }
}
