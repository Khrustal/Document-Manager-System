package com.dms.rest;

import com.dms.dto.CreateDirDto;
import com.dms.model.DocFile;
import com.dms.model.Document;
import com.dms.services.DocFileService;
import com.dms.services.DocumentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/file")
public class DocFileController {

    DocFileService docFileService;
    DocumentService documentService;

    public DocFileController(DocFileService docFileService, DocumentService documentService) {
        this.docFileService = docFileService;
        this.documentService = documentService;
    }

    @GetMapping("/create")
    public String create(@RequestParam Long id, Model model) {
      model.addAttribute("file", new DocFile());
      return "create_file";
    }

    @PostMapping("/create")
    public String create(@RequestParam Long id, @ModelAttribute DocFile file, Model model) {

        docFileService.save(file);

        //Add file to document
        Document document = documentService.find(id);
        document.addFile(file);
        documentService.create(document);

        return "home";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam Long id) {
        DocFile docFile = docFileService.find(id);
        Document document = documentService.fundByFileId(id);
        document.removeFile(docFile);
        documentService.create(document);
        docFileService.delete(docFile);
        return "home";
    }


}
