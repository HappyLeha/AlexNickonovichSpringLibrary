package com.example.demo.controller;

import com.example.demo.dto.*;
import com.example.demo.entity.Book;
import com.example.demo.entity.Rent;
import com.example.demo.entity.User;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.exceptions.ResourceRestrictException;
import com.example.demo.service.BookService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("book")

public class BookController {

    private final BookService bookService;
    private final UserService userService;

    @Autowired
    public BookController(BookService bookService,UserService userService) {
        this.bookService = bookService;
        this.userService=userService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void saveBook( @RequestBody BookDto bookDto) {
        Book book=new Book(bookDto.getId(),bookDto.getTitle(),bookDto.getDate(),bookDto.getCount(),
                bookDto.getAuthors(),bookDto.getPublishing(),null);
        bookService.addNewBook(book);
    }

    @GetMapping(value = {"/{id}"})
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_READER')")
    public BookDto findById(@PathVariable("id") long id) throws ResourceNotFoundException {
        Book book=bookService.getById(id);
        return new BookDto(book.getId(),book.getTitle(),book.getDate(),book.getCount(),
                book.getPublishing(),book.getAuthors());
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<BookListDto> getBookList() {
       List<Book> books=bookService.getAllBook();
       ArrayList<BookListDto> results=new ArrayList<>();
       for (Book book:books) {
           for (Rent rent:book.getRents()) {
             results.add(new BookListDto(book.getId(),book.getTitle(),book.getDate(),
                     book.getPublishing(),rent.getUser().getId(),rent.getId(),
                     rent.getUser().getLogin()));
           }
           for (int i=0;i<book.getCount()-book.getRents().size();i++) {
               results.add(new BookListDto(book.getId(),book.getTitle(),book.getDate(),
                       book.getPublishing(),null,null, null));
           }
       }
       Collections.sort(results);
       return results;
    }

    @GetMapping(value = {"/free"})
    @PreAuthorize("hasRole('ROLE_READER')")
    public List<FreeBookListDto> getFreeBookList(Principal principal) {
        List<Book> books=bookService.getAllFreeBook();
        ArrayList<FreeBookListDto> results=new ArrayList<>();
        for (Book book:books) {
            results.add(new FreeBookListDto(book.getId(),book.getTitle(),book.getDate(),
                    book.getPublishing(),null));
        }
        User user=userService.getByLogin(principal.getName());
        Book book=null;
        if (user.getRent()!=null) book=user.getRent().getBook();
        if (book!=null) {
            Long id=book.getId();
            FreeBookListDto element;
            if (results.stream().anyMatch(i -> i.getId().equals(id))) {
                element = results.stream().filter(i -> i.getId().equals(id)).findFirst().get();
                element.setRentId(user.getRent().getId());
            }
            else results.add(new FreeBookListDto(book.getId(),book.getTitle(),book.getDate(),
                    book.getPublishing(),user.getRent().getId()));

        }
        Collections.sort(results);
        return results;
    }

    @DeleteMapping(value = {"/{id}"})
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void deleteBook(@PathVariable("id") long id) throws ResourceRestrictException {
        bookService.deleteBook(id);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void deleteBooks(@RequestBody ArrayList<BookDeleteDto> books) {
        bookService.deleteBooks(books);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void editBook(@RequestBody BookDto bookDto) throws ResourceNotFoundException,
            ResourceRestrictException {
        Book book=new Book(bookDto.getId(),bookDto.getTitle(), bookDto.getDate(),
                bookDto.getCount(),bookDto.getAuthors(),bookDto.getPublishing(),null);
        bookService.editBook(book);
    }

}
