package com.dms.rest;

import com.dms.model.Directory;
import com.dms.model.Document;
import com.dms.model.Storable;
import com.dms.services.DirectoryService;
import com.dms.services.DocumentService;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class BasicController{

    DirectoryService directoryService;
    DocumentService documentService;

    public BasicController(DirectoryService directoryService, DocumentService documentService) {
        this.directoryService = directoryService;
        this.documentService = documentService;
    }

    @GetMapping("/")
    public String home(Model model){
        UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("message", user.getUsername());
        return "home";
    }

    @GetMapping("/search")
    public String find(@RequestParam String name, Model model) {
        List<Directory> dirs =  directoryService.find(name);
        List<Document> docs =  documentService.find(name);
        List<Storable> contents = new ArrayList<>(dirs);
        contents.addAll(docs);
        model.addAttribute("contents", contents);
        return "content";
    }

}
