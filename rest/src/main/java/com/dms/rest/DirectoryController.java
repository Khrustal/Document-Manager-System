package com.dms.rest;

import com.dms.dto.CreateDirDto;
import com.dms.model.*;
import com.dms.services.*;
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
    StorableService storableService;
    MailSenderService mailSenderService;
    MailConfigService mailConfigService;

    public DirectoryController(DirectoryService directoryService, UserService userService,
                               StorableService storableService, MailSenderService mailSenderService,
                               MailConfigService mailConfigService) {
        this.directoryService = directoryService;
        this.userService = userService;
        this.storableService = storableService;
        this.mailSenderService = mailSenderService;
        this.mailConfigService = mailConfigService;
    }

    @GetMapping("/create")
    public String createForm(@RequestParam(required = false) Long id, Model model) {

        //Check edit rights
        if(id != null) {
            User user = userService.getCurrent();
            if (!storableService.canEdit(id, user) && !storableService.isFreeAccess(id) && !user.isAdmin()) {
                model.addAttribute("message", "Access denied");
                return "info";
            }
        }

        model.addAttribute("parentId", id);
        model.addAttribute("directory", new Directory());
        return "create_dir";
    }

    @PostMapping("/create")
    public String create(@RequestParam(required = false) Long id, @ModelAttribute CreateDirDto dto, Model model) {
        User user = userService.getCurrent();

        //Check edit rights
        if(id != null) {
            Storable storable = storableService.find(id);
            if (!storableService.canEdit(id, user) && !storable.getFreeAccess()  && !user.isAdmin()) {
                model.addAttribute("message", "Access denied");
                return "info";
            }
        }

        Directory parent = null;
        Status status = Status.CURRENT;

        if(id != null) {
            parent = directoryService.find(id).orElseThrow(RuntimeException::new);
            if(parent.getEditors().contains(user)) {
                status = Status.ON_MODERATION;
            }
        }

        Directory directory = new Directory(null, parent, user, dto.getName(), Type.DIRECTORY,
                dto.getFreeAccess(), status, new Timestamp(System.currentTimeMillis()));

        if(parent == null) {
            directory.addModerator(user);
        }
        else {
            directory.addModerators(directory.getParent().getModerators());
            directory.addEditors(directory.getParent().getEditors());
            directory.addReaders(directory.getParent().getReaders());
        }


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
            if(!(reader || editor || moderator) && !directory.getFreeAccess() && !user.isAdmin()) {
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

        Storable storable = storableService.find(id);
        User author = storable.getAuthor();

        //Check if dir is not empty
        if(!directoryService.getContent(id).isEmpty()) {
            model.addAttribute("message", "Directory is not empty");
        }
        else if(!storableService.canDelete(id, userService.getCurrent())
                && !storableService.isFreeAccess(id) && !userService.getCurrent().isAdmin()) {
            model.addAttribute("message", "Access denied");
        }
        else {
            directoryService.delete(id);

            model.addAttribute("message", "Directory deleted");
        }
        if(mailConfigService.getMailStatus()) {
            mailSenderService.send(author,
                "Your directory " + storable.getName() + " deleted");
        }
        return "info";
    }

}
