package com.dms.rest;

import com.dms.dto.CreateDocDto;
import com.dms.model.*;
import com.dms.services.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;

@Controller
@RequestMapping("/doc")
public class DocumentController {

    DocumentService documentService;
    DirectoryService directoryService;
    UserService userService;
    DocTypeService docTypeService;
    StorableService storableService;

    public DocumentController(DocumentService documentService, DirectoryService directoryService,
                              UserService userService, DocTypeService docTypeService,
                              StorableService storableService) {
        this.documentService = documentService;
        this.directoryService = directoryService;
        this.userService = userService;
        this.docTypeService = docTypeService;
        this.storableService = storableService;
    }

    @GetMapping("/create")
    public String createForm(@RequestParam(required = false) Long dir, @RequestParam(required = false) Long doc,
                             Model model) {

        User user = userService.getCurrent();

        //Check edit rights
        if(dir != null) {
            if (!storableService.canEdit(dir, user)) {
                model.addAttribute("message", "Access denied");
                return "info";
            }
        }

        if(doc != null) {
            if (!storableService.canEdit(doc, user)) {
                model.addAttribute("message", "Access denied");
                return "info";
            }
        }

        model.addAttribute("document", new CreateDocDto());
        List<DocType> docTypeList = docTypeService.findAll();
        model.addAttribute("docTypes", docTypeList);
        return "create_doc";
    }

    @PostMapping("/create")
    public String create(@RequestParam(required = false) Long dir, @RequestParam(required = false) Long doc,
                         @ModelAttribute CreateDocDto dto, Model model) {

        User user = userService.getCurrent();

        //Check edit rights
        if(dir != null) {
            if (!storableService.canEdit(dir, user)) {
                model.addAttribute("message", "Access denied");
                return "info";
            }
        }

        if(doc != null) {
            if (!storableService.canEdit(doc, user)) {
                model.addAttribute("message", "Access denied");
                return "info";
            }
        }

        Directory parent = null;
        if(dir != null) {
            parent = directoryService.find(dir).get();
        }
        Document document = new Document(null, parent, user, dto.getName(), Type.DOCUMENT,
                dto.getFreeAccess(), Status.CURRENT, new Timestamp(System.currentTimeMillis()),
                dto.getDescription(), dto.getPriority(), dto.getDocType(), null);

        //Creator gets moderator right
        document.addModerator(user);

        //doc == null => New Document, ancestor = this;
        //doc != null => Version od Document, ancestor = firstVersion
        Document ancestor = document;
        if(doc != null) {
            Document prev = documentService.find(doc);
            prev.setStatus(Status.OLD); //ToDo Change when implement moderation
            documentService.create(prev); //update status
            ancestor = prev.getAncestor();
        }
        document.setAncestor(ancestor);
        documentService.create(document);

        model.addAttribute("message", "Document created");
        return "info";
    }

    @GetMapping("/view")
    public String view(@RequestParam Long id, Model model) {
        Document document = documentService.find(id);

        User user = userService.getCurrent();

        boolean reader = document.getReaders().contains(user);
        boolean editor = document.getEditors().contains(user);
        boolean moderator = document.getModerators().contains(user);

        //If no access return "Access denied"
        if(!(reader || editor || moderator)) {
            model.addAttribute("message", "Access denied");
            return "info";
        }

        model.addAttribute("reader", reader);
        model.addAttribute("editor", editor);
        model.addAttribute("moderator", moderator);

        model.addAttribute("document", document);
        model.addAttribute("files", document.getFiles());
        return "doc";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam Long id, Model model) {

        //ToDo version safety

        if(!storableService.canDelete(id, userService.getCurrent())) {
            model.addAttribute("message", "Access denied");
        }
        else {
            documentService.delete(id);
            model.addAttribute("message", "Document deleted");
        }
        return "info";
    }
}
