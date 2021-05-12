package com.dms.rest;

import com.dms.model.Storable;
import com.dms.model.User;
import com.dms.services.StorableService;
import com.dms.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {

    UserService userService;
    StorableService storableService;

    public UserController(UserService userService, StorableService storableService) {
        this.userService = userService;
        this.storableService = storableService;
    }

    @GetMapping("/grant")
    public String grant(@RequestParam Long id, @RequestParam String right, Model model) {
        List<User> users = userService.findAll();
        //Remove current user
        users.remove(userService.getCurrent());
        model.addAttribute("users", users);
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
            message = "Access Denied";
        }
        model.addAttribute("message", message);
        return "info";
    }

}
