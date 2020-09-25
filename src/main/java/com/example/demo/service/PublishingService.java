package com.example.demo.service;

import com.example.demo.entity.Book;
import com.example.demo.entity.Publishing;
import com.example.demo.exceptions.ResourceNoContentException;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.exceptions.ResourceRestrictException;

import java.util.List;

public interface PublishingService {
    List<Publishing> getAllPublishing();
    void addNewPublishing (Publishing publishing);
    void deletePublishing(Integer publishingId) throws ResourceRestrictException;
    void editPublishing(Publishing publishing) throws ResourceNotFoundException;
    Publishing getById(Integer id) throws ResourceNotFoundException;
}
