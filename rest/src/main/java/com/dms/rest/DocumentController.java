package com.dms.rest;

import com.dms.dto.CreateDocDto;
import com.dms.model.*;
import com.dms.services.*;
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

        if(dir != null && !storableService.isFreeAccess(dir) && !storableService.isFreeAccess(doc)) {
            //Check edit rights
            if (!storableService.canEdit(dir, user)) {
                model.addAttribute("message", "Access denied");
                return "info";
            }

            if (doc != null) {
                if (!storableService.canEdit(doc, user)) {
                    model.addAttribute("message", "Access denied");
                    return "info";
                }
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

        if(doc != null) {
            if (!storableService.canEdit(doc, user) && !storableService.isFreeAccess(doc)) {
                model.addAttribute("message", "Access denied");
                return "info";
            }

            Document prev = documentService.find(doc);

            //Check if doc on moderation
            if(documentService.findModerated(prev.getAncestor()).isPresent()) {
                model.addAttribute("message", "Document is currently on moderation");
                return "info";
            }

        }

        Directory parent = null;

        Status status = Status.CURRENT;

        if(dir != null) {
            if (!storableService.canEdit(dir, user) && !storableService.isFreeAccess(dir)) {
                model.addAttribute("message", "Access denied");
                return "info";
            }
            parent = directoryService.find(dir).orElseThrow(RuntimeException::new);

            if(parent.getEditors().contains(user)) {
                status = Status.ON_MODERATION;
            }
        }


        Document document = new Document(null, parent, user, dto.getName(), Type.DOCUMENT,
                dto.getFreeAccess(), status, new Timestamp(System.currentTimeMillis()),
                dto.getDescription(), dto.getPriority(), dto.getDocType(), null);

        //Creator gets moderator right
        document.addModerator(user);

        //doc == null => New Document, ancestor = this;
        //doc != null => Version od Document, ancestor = firstVersion
        Document ancestor = document;
        if(doc != null) {
            Document prev = documentService.find(doc);

            if(prev.getEditors().contains(user)) {
                status = Status.ON_MODERATION;
            }
            else {
                prev.setStatus(Status.OLD);
                documentService.create(prev);
            }

            document.addModerators(prev.getModerators());
            document.addEditors(prev.getEditors());
            document.addReaders(prev.getReaders());

            document.setStatus(status);

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
        if(!(reader || editor || moderator) && !storableService.isFreeAccess(id) && !user.isAdmin()) {
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

        User current = userService.getCurrent();

        if(!storableService.canDelete(id, current) && !storableService.isFreeAccess(id) && !current.isAdmin()) {
            model.addAttribute("message", "Access denied");
        }
        else {
            Document document = documentService.find(id);
            if(document.equals(document.getAncestor())) {
                //If initial doc and other versions exists
                List<Document> versions = documentService.findByAncestor(document);
                versions.remove(document);
                if(!versions.isEmpty()) {
                    model.addAttribute("message", "Can't delete initial version before others exist");
                }
                else {
                    documentService.delete(id);
                    model.addAttribute("message", "Document deleted");
                }
            }
            //If deleting current version and others exist -> make previous current
            else if (document.getStatus().equals(Status.CURRENT)) {
                Document prev = documentService.findLatestOld(document).orElseThrow(RuntimeException::new);
                prev.setStatus(Status.CURRENT);
                documentService.delete(id);
                model.addAttribute("message", "Document deleted");
            }
        }
        return "info";
    }
}
