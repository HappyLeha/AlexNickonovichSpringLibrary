package com.example.demo.controller;

import com.example.demo.dto.RentCreateDto;
import com.example.demo.dto.RentDto;
import com.example.demo.entity.Book;
import com.example.demo.entity.User;
import com.example.demo.entity.Rent;
import com.example.demo.exceptions.ResourceNoContentException;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.exceptions.ResourceRestrictException;
import com.example.demo.service.BookService;
import com.example.demo.service.UserService;
import com.example.demo.service.RentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("rent")

public class RentController {

    private final UserService userService;
    private final BookService bookService;
    private final RentService rentService;

    @Autowired
    public RentController(UserService userService, BookService bookService, RentService rentService) {
        this.userService = userService;
        this.bookService = bookService;
        this.rentService = rentService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_READER')")
    public void saveRent(  @RequestBody RentCreateDto rentDto) throws ResourceNoContentException,
            ResourceRestrictException {
        User user =null;
        Book book=null;
        try {
            user = userService.getById(rentDto.getUser());
            book=bookService.getById(rentDto.getBook());
        }
        catch (Exception e) {
           throw new ResourceNoContentException();
        }
        rentService.addNewRent(new Rent(rentDto.getId(), user,book,rentDto.getStartDate(),
                rentDto.getEndDate()));
    }

    @GetMapping(value = {"/{id}"})
    @PreAuthorize("hasRole('ROLE_READER') or hasRole('ROLE_ADMIN')")
    public RentDto findById(@PathVariable("id") long id) throws
            ResourceNotFoundException {
        Rent rent=rentService.getById(id);
        return new RentDto(rent.getUser().getLogin(),rent.getBook().getTitle(),
                rent.getStartDate(), rent.getEndDate());
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_READER')")
    public void deleteRent(Principal principal)  {
        User user=userService.getByLogin(principal.getName());
        if (user.getRent()==null) return;
        rentService.deleteRent(user.getRent().getId());
    }

}
