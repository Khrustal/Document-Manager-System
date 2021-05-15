package com.dms.rest;

import com.dms.dto.UserDto;
import com.dms.model.*;
import com.dms.services.*;
import com.dms.services.mappers.DirectoryDtoMapper;
import com.dms.services.mappers.UserDtoMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/users")
public class UserController {

    UserService userService;
    StorableService storableService;
    DirectoryService directoryService;
    DocumentService documentService;
    MailSenderService mailSenderService;
    MailConfigService mailConfigService;

    public UserController(UserService userService, StorableService storableService,
                          DirectoryService directoryService, DocumentService documentService,
                          MailSenderService mailSenderService, MailConfigService mailConfigService) {
        this.userService = userService;
        this.storableService = storableService;
        this.directoryService = directoryService;
        this.documentService = documentService;
        this.mailSenderService = mailSenderService;
        this.mailConfigService = mailConfigService;
    }

    @GetMapping("/grant")
    public String grant(@RequestParam Long id, @RequestParam String right, Model model) {
        List<User> users = userService.findAll();
        //Remove current user
        //ToDo remove users with access == right
        users.remove(userService.getCurrent());

        List<UserDto> dtos = users.stream().map(UserDtoMapper::map).collect(Collectors.toList());

        model.addAttribute("users", dtos);
        return "users";
    }

    @PostMapping("/grant")
    public String grant(@RequestParam Long id, @RequestParam String right, @RequestParam Long userId, Model model) {
        String message;
        User user = userService.find(userId).orElseThrow(RuntimeException::new);
        Storable storable = storableService.find(id);

        //Check access
        if(userService.checkGrant(storable, user, right)) {
            switch (right) {
                case "moderator" -> storable.addModerator(user);
                case "editor" -> storable.addEditor(user);
                case "reader" -> storable.addReader(user);
            }
            storableService.save(storable);
            message = "Granted " + right + " to " + user.getUsername();
        }
        else {
            message = "Request can't be fulfilled";
        }
        model.addAttribute("message", message);
        return "info";
    }

    @GetMapping("/moderation")
    public String getModerationList(@RequestParam(required = false) Long id,  Model model) {

        if(id == null) {
            List<Storable> moderationList = storableService.getModerationList(userService.getCurrent());

            model.addAttribute("contents", moderationList);

            return "moderation-list";
        }
        else {
            Storable storable = storableService.find(id);
            if(storable.getType().equals(Type.DIRECTORY)) {
                model.addAttribute("content", DirectoryDtoMapper.map(storable));
            }
            else {
                Document document = documentService.find(id);
//                DocumentDto dto = DocumentDtoMapper.map(document);
                model.addAttribute("content", document);
                model.addAttribute("files", document.getFiles());
            }
            return "moderation";
        }

    }

    @PostMapping("/moderation")
    public String moderate(@RequestParam Long id, @RequestParam Boolean value, Model model) {

        Storable storable = storableService.find(id);
        User author = storable.getAuthor();


        if(storable.getModerators().contains(userService.getCurrent())) {
            if(value) {
                //Version accepted -> Change status to current and make previous status OLD
                if(storable.getType().equals(Type.DOCUMENT)) {
                    Optional<Document> prev = documentService.findPrevModerated((Document) storable);
                    prev.ifPresent(document -> document.setStatus(Status.OLD));
                }
                storable.setStatus(Status.CURRENT);
                //Creator becomes moderator
                storable.addModerator(storable.getAuthor());
                model.addAttribute("message", "Accepted " + storable.getName());
                storableService.save(storable);
                if(mailConfigService.getMailStatus()) {
                    mailSenderService.send(author,
                            "Your " + storable.getType().toString().toLowerCase() + " " +
                                    storable.getName() + " accepted");
                }
            }
            else {
                //Version declined -> delete version
                Document prev = documentService.findLatestOld((Document) storable).orElseThrow(RuntimeException::new);
                prev.setStatus(Status.CURRENT);
                documentService.create(prev);

                model.addAttribute("message", "Declined " + storable.getName());

                documentService.delete(id);
                if(mailConfigService.getMailStatus()) {
                    mailSenderService.send(author,
                            "Your " + storable.getType().toString().toLowerCase() + " " +
                                    storable.getName() + " declined");
                }
            }
        }
        else {
            model.addAttribute("message", "Access denied");
        }

        return "info";
    }

}
