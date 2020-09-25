package com.example.demo.controller;

import com.example.demo.dto.AuthorDto;
import com.example.demo.dto.AuthorListDto;

import com.example.demo.entity.Author;

import com.example.demo.exceptions.ResourceAlreadyCreateException;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.exceptions.ResourceRestrictException;
import com.example.demo.service.AuthorService;
import com.example.demo.util.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("author")
public class AuthorController {
    private final AuthorService authorService;
    @Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }
    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public void saveAuthor(  @RequestBody AuthorDto authorDto) {
        authorService.addNewAuthor(Mapper.map(authorDto, Author.class));
    }
    @GetMapping(value = {"/{id}"})
    public AuthorDto findById(@PathVariable("id") int id) throws
            ResourceNotFoundException {
        return Mapper.map(authorService.getById(id), AuthorDto.class);
    }

    @GetMapping(value = {"/all"})
    public List<AuthorListDto> authorList() {
        return Mapper.mapAll(authorService.getAllAuthor(), AuthorListDto.class);
    }
    @DeleteMapping(value = {"/delete/{id}"})
    @ResponseStatus(HttpStatus.OK)
    public void deleteAuthor(@PathVariable("id") int id) throws ResourceRestrictException {
        authorService.deleteAuthor(id);
    }
    @PutMapping(value = "/edit")
    @ResponseStatus(HttpStatus.OK)
    public void editAuthor(@RequestBody AuthorDto authorDto) throws ResourceNotFoundException {
        authorService.editAuthor(Mapper.map(authorDto, Author.class));
    }
}
