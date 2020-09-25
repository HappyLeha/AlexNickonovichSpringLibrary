package com.example.demo.service;

import com.example.demo.dto.SearchDto;
import com.example.demo.entity.Author;
import com.example.demo.entity.Book;
import com.example.demo.exceptions.ResourceNoContentException;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.exceptions.ResourceRestrictException;

import java.util.List;

public interface BookService {
    List<Book> getAllBook();
    void addNewBook (Book book);
    void deleteBook(Integer bookId) throws ResourceRestrictException;
    void editBook(Book book) throws ResourceNotFoundException,ResourceRestrictException;
    Book getById(Integer id) throws ResourceNotFoundException;
    List<Book> search (SearchDto searchDto);
}
