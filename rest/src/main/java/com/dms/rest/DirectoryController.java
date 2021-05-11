package com.dms.rest;

import com.dms.dto.CreateDirDto;
import com.dms.model.Directory;
import com.dms.model.Status;
import com.dms.model.Type;
import com.dms.model.User;
import com.dms.services.DirectoryService;
import com.dms.services.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;

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
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.find(userDetails.getUsername()).get();
        Directory parent = null;
        if(id != null) {
            parent = directoryService.find(id).get();
        }
        Directory directory = new Directory(null, parent, user, dto.getName(), Type.DIRECTORY,
                dto.getFreeAccess(), Status.CURRENT, new Timestamp(System.currentTimeMillis()));
        directoryService.create(directory);
        return "home";
    }

    @GetMapping("/content")
    public String getContent(@RequestParam(required = false) Long id , Model model) {
        model.addAttribute("contents", directoryService.getContent(id));
        model.addAttribute("type", Type.values());
        return "content";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam Long id, Model model) {
        directoryService.delete(id);
        return "home";
    }

}
