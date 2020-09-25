package com.example.demo.service;

import com.example.demo.entity.Author;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.exceptions.ResourceRestrictException;

import java.util.List;

public interface AuthorService {
    List<Author> getAllAuthor();
    void addNewAuthor(Author author);
    void deleteAuthor(Integer authorId) throws ResourceRestrictException;
    void editAuthor(Author author) throws ResourceNotFoundException;
    Author getById(Integer id) throws ResourceNotFoundException;
}
