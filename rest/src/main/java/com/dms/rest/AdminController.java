package com.dms.rest;

import com.dms.model.*;
import com.dms.services.*;
import org.dom4j.rule.Mode;
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
    MailConfigService mailConfigService;

    public AdminController(DocTypeService docTypeService, DirectoryService directoryService,
                           DocumentService documentService, StorableService storableService,
                           MailConfigService mailConfigService) {
        this.docTypeService = docTypeService;
        this.directoryService = directoryService;
        this.documentService = documentService;
        this.storableService = storableService;
        this.mailConfigService = mailConfigService;
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("status", mailConfigService.getMailStatus());
        return "admin_home";
    }

    @PostMapping("/mail")
    public String changeMailStatus(@RequestParam boolean status, Model model) {
        if (status) {
            mailConfigService.enableMail();
            model.addAttribute("message", "Mail enabled");
        } else {
            mailConfigService.disableMail();
            model.addAttribute("message", "Mail disabled");
        }
        return "info";
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
