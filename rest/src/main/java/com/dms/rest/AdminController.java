package com.dms.rest;

import com.dms.model.*;
import com.dms.services.DirectoryService;
import com.dms.services.DocTypeService;
import com.dms.services.DocumentService;
import com.dms.services.StorableService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminController {

    DocTypeService docTypeService;
    DirectoryService directoryService;
    DocumentService documentService;
    StorableService storableService;

    public AdminController(DocTypeService docTypeService, DirectoryService directoryService, DocumentService documentService, StorableService storableService) {
        this.docTypeService = docTypeService;
        this.directoryService = directoryService;
        this.documentService = documentService;
        this.storableService = storableService;
    }

    @GetMapping("/")
    public String home() {
        return "admin_home";
    }

    @GetMapping("/doctype")
    public String getDocTypes(Model model) {

        model.addAttribute("contents", docTypeService.findAll());
        model.addAttribute("doctype", new DocType());

        return "admin_doctype";
    }

    @PostMapping("/doctype/create")
    public String create(@RequestParam String name, Model model) {
        docTypeService.create(new DocType(name.replaceAll(",", "")));
        model.addAttribute("message", "Doctype created");
        return "info";
    }

    @PostMapping("/doctype/delete")
    public String delete(@RequestParam Long id, Model model) {

        docTypeService.delete(id);

        model.addAttribute("message", "Doctype deleted");
        return "info";
    }

}
