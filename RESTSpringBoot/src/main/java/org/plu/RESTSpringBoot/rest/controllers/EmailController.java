package org.plu.RESTSpringBoot.rest.controllers;

import org.plu.RESTSpringBoot.rest.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.Console;

@CrossOrigin
@RequestMapping("/confirmation")
@RestController
public class EmailController {
    @Autowired
    private EmailService emailService;

    @GetMapping(value = "/{token}")
    public boolean confirm(@PathVariable String token) {
         System.out.println("Email token!!!!");
         return emailService.confirmActivation(token);
    }
}
