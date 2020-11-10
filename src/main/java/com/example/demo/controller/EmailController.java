package com.example.demo.controller;

import com.example.demo.dto.EmailCreateDto;
import com.example.demo.exceptions.ResourceNoContentException;
import com.example.demo.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import javax.mail.MessagingException;

@RestController
@RequestMapping("email")

public class EmailController {

    private final EmailService emailService;
    private final UserService userService;

    @Autowired
    public EmailController(EmailService emailService, UserService userService) {
        this.emailService=emailService;
        this.userService=userService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void sendEmail( @RequestBody EmailCreateDto email) throws ResourceNoContentException,
            MessagingException {
        for (String address:email.getEmails()) {
          if (userService.getAllReader().stream().filter(i->i.getEmail().equals(address)).count()==0) {
              throw new ResourceNoContentException();
          }
        }
        emailService.sendEmail(email);
    }

}
