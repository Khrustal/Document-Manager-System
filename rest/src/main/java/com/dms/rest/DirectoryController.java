package com.dms.rest;

import com.dms.dto.CreateDirDto;
import com.dms.model.*;
import com.dms.services.DirectoryService;
import com.dms.services.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;

@Controller
@RequestMapping("/dir")
public class DirectoryController {

    DirectoryService directoryService;
    UserService userService;

    public DirectoryController(DirectoryService directoryService, UserService userService) {
        this.directoryService = directoryService;
        this.userService = userService;
    }

    @GetMapping("/create")
    public String createForm(@RequestParam(required = false) Long id, Model model) {
        model.addAttribute("parentId", id);
        model.addAttribute("directory", new Directory());
        return "create_dir";
    }

    @PostMapping("/create")
    public String create(@RequestParam(required = false) Long id, @ModelAttribute CreateDirDto dto, Model model) {
        User user = userService.getCurrent();
        Directory parent = null;
        if(id != null) {
            parent = directoryService.find(id).orElseThrow(RuntimeException::new);
        }

        Directory directory = new Directory(null, parent, user, dto.getName(), Type.DIRECTORY,
                dto.getFreeAccess(), Status.CURRENT, new Timestamp(System.currentTimeMillis()));

        directory.addModerator(user);

        directoryService.create(directory);

        model.addAttribute("message", "Directory created");
        return "info";
    }

    @GetMapping("/content")
    public String getContent(@RequestParam(required = false) Long id , Model model) {

        List<Storable> contents = directoryService.getContent(id);

        //Check rights
        if(id != null) {
            Directory directory = directoryService.find(id).orElseThrow(RuntimeException::new);

            User user = userService.getCurrent();

            boolean reader = directory.getReaders().contains(user);
            boolean editor = directory.getEditors().contains(user);
            boolean moderator = directory.getModerators().contains(user);

            //If no access return "Access denied"
            if(!(reader || editor || moderator)) {
                model.addAttribute("message", "Access denied");
                return "info";
            }

            model.addAttribute("reader", reader);
            model.addAttribute("editor", editor);
            model.addAttribute("moderator", moderator);
        }
        model.addAttribute("contents", contents);
        model.addAttribute("type", Type.values());
        return "content";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam Long id, Model model) {
        directoryService.delete(id);

        model.addAttribute("message", "Directory deleted");

        return "info";
    }

}
