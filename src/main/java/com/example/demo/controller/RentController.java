package com.example.demo.controller;

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
import org.springframework.web.bind.annotation.*;

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
    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public void saveRent(  @RequestBody RentDto rentDto) throws ResourceNoContentException {
        User user =null;
        Book book=null;
        try {
            user = userService.getById(rentDto.getUser());
            book=bookService.getById(rentDto.getBook());
        }
        catch (Exception e) {
           throw new ResourceNoContentException();
        }
        rentService.addNewRent(new Rent(rentDto.getId(), user,book,rentDto.getStartDate(),rentDto.getEndDate()));
    }
    @GetMapping(value = {"/{id}"})
    public RentDto findById(@PathVariable("id") int id) throws
            ResourceNotFoundException {
        Rent rent=rentService.getById(id);
        return new RentDto(rent.getId(),rent.getUser().getId(),rent.getBook().getId(),
                rent.getStartDate(), rent.getEndDate());

    }
    @DeleteMapping(value = {"/delete/{id}"})
    @ResponseStatus(HttpStatus.OK)
    public void deleteRent(@PathVariable("id") int id)  {
        rentService.deleteRent(id);
    }
}
