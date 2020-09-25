package com.example.demo.service;

import com.example.demo.entity.Author;
import com.example.demo.entity.Cover;
import com.example.demo.exceptions.ResourceNoContentException;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.exceptions.ResourceRestrictException;

import java.util.List;

public interface CoverService {
    List<Cover> getAllCover();
    void addNewCover(Cover cover);
    void deleteCover(Integer coverId);
    void editCover(Cover cover) throws ResourceNotFoundException;
    Cover getById(Integer id) throws ResourceNotFoundException;
}
