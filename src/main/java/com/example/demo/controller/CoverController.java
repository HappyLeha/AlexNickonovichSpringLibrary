package com.example.demo.controller;

import com.example.demo.dto.BookDto;
import com.example.demo.dto.BookListDto;
import com.example.demo.dto.CoverDto;
import com.example.demo.entity.Author;
import com.example.demo.entity.Book;
import com.example.demo.entity.Cover;
import com.example.demo.entity.Publishing;
import com.example.demo.exceptions.ResourceNoContentException;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.exceptions.ResourceRestrictException;
import com.example.demo.service.AuthorService;
import com.example.demo.service.BookService;
import com.example.demo.service.CoverService;
import com.example.demo.service.PublishingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("cover")
public class CoverController {
    private final BookService bookService;
    private final CoverService coverService;
    @Autowired
    public CoverController(BookService bookService, CoverService coverService) {
        this.bookService=bookService;
        this.coverService=coverService;
    }
    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public void saveCover( @RequestBody CoverDto coverDto) throws ResourceNoContentException {
        Book book;
        try {
            if (coverDto.getBook()!=null) book=bookService.getById(coverDto.getBook());
            else book=null;
        }
        catch (Exception e) {
            throw new ResourceNoContentException();
        }
        Cover cover=new Cover(coverDto.getId(),coverDto.getPhoto(),coverDto.getDate(),coverDto.getNote(),book);
        coverService.addNewCover(cover);
    }
    @GetMapping(value = {"/{id}"})
    public CoverDto findById(@PathVariable("id") int id) throws
            ResourceNotFoundException {
        Cover cover=coverService.getById(id);
       Integer book=cover.getBook()==null?null:cover.getBook().getId();
        return new CoverDto(cover.getId(),cover.getPhoto(),cover.getDate(),cover.getNote(),book);

    }
    @GetMapping(value = {"/all"})
    public List<CoverDto> coverList() {
        List<Cover> covers=coverService.getAllCover();
        ArrayList<CoverDto> results=new ArrayList<>();
        for (Cover cover:covers) {
            Integer book=cover.getBook()==null?null:cover.getBook().getId();
            results.add(new CoverDto(cover.getId(),cover.getPhoto(),cover.getDate(),cover.getNote(),book));
        }
        return results;
    }
    @DeleteMapping(value = {"/delete/{id}"})
    @ResponseStatus(HttpStatus.OK)
    public void deleteCover(@PathVariable("id") int id)  {
        coverService.deleteCover(id);
    }
    @PutMapping(value = "/edit")
    @ResponseStatus(HttpStatus.OK)
    public void editCover(@RequestBody CoverDto coverDto) throws ResourceNotFoundException,ResourceNoContentException {
        Book book;
        try {
            if (coverDto.getBook()!=null) book=bookService.getById(coverDto.getBook());
            else book=null;
        }
        catch (Exception e) {
            throw new ResourceNoContentException();
        }
        coverService.editCover(new Cover(coverDto.getId(),coverDto.getPhoto(),coverDto.getDate(),coverDto.getNote(),book));
    }
}
