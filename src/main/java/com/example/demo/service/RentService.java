package com.example.demo.service;

import com.example.demo.entity.Rent;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.exceptions.ResourceRestrictException;

public interface RentService {

    void addNewRent (Rent rent) throws ResourceRestrictException;
    void deleteRent (Long rentId);
    Rent getById(Long rentId) throws ResourceNotFoundException;

}
