package com.dms.rest;

import com.dms.model.Directory;
import com.dms.model.Status;
import com.dms.model.Type;
import com.dms.model.User;
import com.dms.services.DirectoryService;
import com.dms.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.sql.Timestamp;
import java.util.Optional;

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
    public String createForm(Model model) {
        return "create_dir";
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(Model model) {

        Optional<User> user = userService.find(1L);

        Directory directory = new Directory(null, null, user.get(), "example", Type.DIRECTORY,
                true, Status.CURRENT, new Timestamp(System.currentTimeMillis()));

        directoryService.create(directory);

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
