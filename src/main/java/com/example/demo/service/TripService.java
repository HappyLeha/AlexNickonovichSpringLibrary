package com.example.demo.service;

import com.example.demo.entity.Trip;
import com.example.demo.entity.User;
import com.example.demo.exceptions.ResourceAlreadyCreateException;
import com.example.demo.exceptions.ResourceNotFoundException;

import java.util.List;

public interface TripService {
    List<Trip> getAllTrip();
    void addNewTrip(Trip trip) throws ResourceAlreadyCreateException;
    void deleteTrip(Trip trip);
    void editTrip(Trip trip,Integer id);
    Trip getById(Integer id) throws ResourceNotFoundException;
}
