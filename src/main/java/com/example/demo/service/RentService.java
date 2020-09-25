package com.example.demo.service;

import com.example.demo.entity.Book;
import com.example.demo.entity.Rent;
import com.example.demo.exceptions.ResourceNoContentException;
import com.example.demo.exceptions.ResourceNotFoundException;

public interface RentService {
    void addNewRent (Rent rent);
    void deleteRent (Integer rentId);
    Rent getById(Integer rentId) throws ResourceNotFoundException;
}
