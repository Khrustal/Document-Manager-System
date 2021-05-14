package com.dms.rest;

import com.dms.model.DocFile;
import com.dms.model.DocType;
import com.dms.services.DocTypeService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminController {

    DocTypeService docTypeService;

    public AdminController(DocTypeService docTypeService) {
        this.docTypeService = docTypeService;
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
