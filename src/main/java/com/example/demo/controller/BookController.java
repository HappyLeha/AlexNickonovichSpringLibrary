package com.example.demo.controller;

import com.example.demo.dto.*;
import com.example.demo.entity.Author;
import com.example.demo.entity.Book;
import com.example.demo.entity.Publishing;
import com.example.demo.exceptions.ResourceNoContentException;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.exceptions.ResourceRestrictException;
import com.example.demo.service.AuthorService;
import com.example.demo.service.BookService;
import com.example.demo.service.PublishingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("book")
public class BookController {
    private final BookService bookService;
    private final AuthorService authorService;
    private final PublishingService publishingService;
    @Autowired
    public BookController(BookService bookService,AuthorService authorService,PublishingService publishingService) {
        this.authorService=authorService;
        this.bookService = bookService;
        this.publishingService=publishingService;
    }
    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public void saveBook( @RequestBody BookDto bookDto) throws ResourceNoContentException {
        List<Author> authors=new ArrayList<>();
        Publishing publishing;
        Book book=new Book(bookDto.getId(),bookDto.getTitle(),bookDto.getYear(),
                bookDto.getDate(),bookDto.getCount(),null,null,null,null);
        for (Integer author:bookDto.getAuthors()) {
            try {
                authors.add(authorService.getById(author));
            }
            catch (Exception e) {
                throw new ResourceNoContentException();
            }
        }
        try {
            if (bookDto.getPublishing()!=null) publishing=publishingService.getById(bookDto.getPublishing());
            else publishing=null;
        }
        catch (Exception e) {
            throw new ResourceNoContentException();
        }
        book.setPublishing(publishing);
        book.setAuthors(authors);
        bookService.addNewBook(book);
    }
    @GetMapping(value = {"/{id}"})
    public BookDto findById(@PathVariable("id") int id) throws
            ResourceNotFoundException {
        List<Integer> authors=new ArrayList<>();
        Book book=bookService.getById(id);
        for (Author author:book.getAuthors()) {
            authors.add(author.getId());
        }
        Integer publishing=book.getPublishing()==null?null:book.getPublishing().getId();
        return new BookDto(book.getId(),book.getTitle(),book.getYear(),book.getDate(),book.getCount(),publishing,authors);

    }
    @GetMapping(value = {"/all"})
    public List<BookListDto> bookList() {
       List<Book> books=bookService.getAllBook();
       ArrayList<BookListDto> results=new ArrayList<>();
       for (Book book:books) {
           Integer publishing=book.getPublishing()==null?null:book.getPublishing().getId();
           results.add(new BookListDto(book.getId(),book.getTitle(),book.getDate(),book.getCount(),publishing));
       }
       return results;
    }
    @DeleteMapping(value = {"/delete/{id}"})
    @ResponseStatus(HttpStatus.OK)
    public void deleteBook(@PathVariable("id") int id) throws ResourceRestrictException {
        bookService.deleteBook(id);
    }
    @PutMapping(value = "/edit")
    @ResponseStatus(HttpStatus.OK)
    public void editBook(@RequestBody BookDto bookDto) throws ResourceNotFoundException,ResourceRestrictException,ResourceNoContentException {
        List<Author> authors=new ArrayList<>();
        Publishing publishing;
        Book book=new Book(bookDto.getId(),bookDto.getTitle(),bookDto.getYear(),
                bookDto.getDate(),bookDto.getCount(),null,null,null,null);
        for (Integer author:bookDto.getAuthors()) {
            try {
                authors.add(authorService.getById(author));
            }
            catch (Exception e) {
                throw new ResourceNoContentException();
            }
        }
        try {
            if (bookDto.getPublishing()!=null) publishing=publishingService.getById(bookDto.getPublishing());
            else publishing=null;
        }
        catch (Exception e) {
            throw new ResourceNoContentException();
        }
        book.setPublishing(publishing);
        book.setAuthors(authors);
        bookService.editBook(book);
    }
    @PostMapping("/search")
    public List<BookListDto> searchBook(@RequestBody SearchDto searchDto) {
        List<Book> books=bookService.search(searchDto);
        ArrayList<BookListDto> results=new ArrayList<>();
        for (Book book:books) {
            Integer publishing=book.getPublishing()==null?null:book.getPublishing().getId();
            results.add(new BookListDto(book.getId(),book.getTitle(),book.getDate(),book.getCount(),publishing));
        }
        return results;
    }
}
