package com.example.demo.controller;

import com.example.demo.dto.AuthorDto;
import com.example.demo.dto.AuthorListDto;
import com.example.demo.dto.PublishingDto;
import com.example.demo.dto.PublishingListDto;
import com.example.demo.entity.Author;
import com.example.demo.entity.Publishing;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.exceptions.ResourceRestrictException;
import com.example.demo.service.AuthorService;
import com.example.demo.service.PublishingService;
import com.example.demo.util.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("publishing")
public class PublishingController {
    private final PublishingService publishingService;
    @Autowired
    public PublishingController(PublishingService publishingService) {
        this.publishingService = publishingService;
    }
    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public void saveAuthor(  @RequestBody PublishingDto publishingDto) {
        publishingService.addNewPublishing(Mapper.map(publishingDto, Publishing.class));
    }
    @GetMapping(value = {"/{id}"})
    public PublishingDto findById(@PathVariable("id") int id) throws
            ResourceNotFoundException {
        return Mapper.map(publishingService.getById(id), PublishingDto.class);
    }
    @GetMapping(value = {"/all"})
    public List<PublishingListDto> publishingList() {
        return Mapper.mapAll(publishingService.getAllPublishing(), PublishingListDto.class);
    }
    @DeleteMapping(value = {"/delete/{id}"})
    @ResponseStatus(HttpStatus.OK)
    public void deletePublishing(@PathVariable("id") int id) throws ResourceRestrictException {
        publishingService.deletePublishing(id);
    }
    @PutMapping(value = "/edit")
    @ResponseStatus(HttpStatus.OK)
    public void editPublishing(@RequestBody PublishingDto publishingDto) throws ResourceNotFoundException {
        publishingService.editPublishing(Mapper.map(publishingDto, Publishing.class));
    }
}
