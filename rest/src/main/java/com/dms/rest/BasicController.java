package com.dms.rest;

import com.dms.model.Directory;
import com.dms.model.Document;
import com.dms.model.Storable;
import com.dms.model.User;
import com.dms.services.DirectoryService;
import com.dms.services.DocumentService;
import com.dms.services.UserService;
import com.dms.services.mappers.UserDtoMapper;
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
    UserService userService;

    public BasicController(DirectoryService directoryService, DocumentService documentService, UserService userService) {
        this.directoryService = directoryService;
        this.documentService = documentService;
        this.userService = userService;
    }

    @GetMapping("/")
    public String home(Model model){
        User user = userService.getCurrent();
        model.addAttribute("user", UserDtoMapper.map(user));
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
