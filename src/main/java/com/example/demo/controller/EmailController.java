package com.example.demo.controller;

import com.example.demo.dto.EmailDto;
import com.example.demo.exceptions.ResourceNoContentException;
import com.example.demo.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
    @PostMapping("/send")
    @ResponseStatus(HttpStatus.OK)
    public void sendEmail( @RequestBody EmailDto email) throws ResourceNoContentException {
        for (String address:email.getEmails()) {
          if (userService.getAllUser().stream().filter(i->i.getEmail().equals(address)).count()==0) throw new ResourceNoContentException();
        }
        emailService.sendEmail(email);
    }
}
