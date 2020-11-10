package com.example.demo.service;

import com.example.demo.dto.BookDeleteDto;
import com.example.demo.entity.Book;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.exceptions.ResourceRestrictException;
import java.util.ArrayList;
import java.util.List;

public interface BookService {

    List<Book> getAllBook();
    List<Book> getAllFreeBook();
    void addNewBook (Book book);
    void deleteBook(Long bookId) throws ResourceRestrictException;
    void editBook(Book book) throws ResourceNotFoundException,ResourceRestrictException;
    Book getById(Long id) throws ResourceNotFoundException;
    void deleteBooks(ArrayList<BookDeleteDto> books);

}
