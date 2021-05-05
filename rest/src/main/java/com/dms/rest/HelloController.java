package com.dms.rest;

import com.dms.dao.TutorialRepository;
import com.dms.model.Tutorial;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class HelloController {

    @Autowired
    TutorialRepository tutorialRepository;

    @GetMapping("/hello")
    public String getHello() {
        return "Hello controller";
    }

    @PostMapping("/create")
    public ResponseEntity<?> createTutorial(@RequestBody Tutorial tutorial) {
        tutorialRepository.save(tutorial);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Tutorial>> getAll() {
        List<Tutorial> tutorialList = new ArrayList<>(tutorialRepository.findAll());
        return new ResponseEntity<>(tutorialList, HttpStatus.OK);
    }
}
