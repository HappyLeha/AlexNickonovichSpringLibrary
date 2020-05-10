package com.example.demo.controller;

import com.example.demo.dto.TripDto;
import com.example.demo.entity.Trip;
import com.example.demo.exceptions.ResourceAlreadyCreateException;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.service.TripService;
import com.example.demo.service.UserService;
import com.example.demo.util.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("trips")
public class TripController {
    private final TripService tripService;
    private final UserService userService;
    @Autowired
    public TripController(TripService tripService,UserService userService) {
        this.tripService = tripService;
        this.userService=userService;
    }
    @GetMapping(value = {"/all"})
    public List<TripDto> tripList() {
        return Mapper.convertTripList(tripService.getAllTrip());
    }
    @GetMapping(value = {"/{id}"})
    public TripDto findById(@PathVariable("id") Integer id) throws
            ResourceNotFoundException {
        return Mapper.convertTrip(tripService.getById(id));
    }
    @PutMapping(value = "/edit/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void editTrip(@PathVariable("id") Integer id,@RequestBody
            TripDto tripDto) throws ResourceNotFoundException {
        tripService.getById(id);
        Trip trip=Mapper.map(tripDto, Trip.class);
        trip.setDriver(userService.getByLogin(tripDto.getDriver()));
        tripService.editTrip(trip,id);
    }
    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public void saveTrip(@RequestBody TripDto tripDto) throws ResourceAlreadyCreateException,ResourceNotFoundException {
        Trip trip=Mapper.map(tripDto, Trip.class);
        trip.setDriver(userService.getByLogin(tripDto.getDriver()));
        tripService.addNewTrip(trip);
    }
    @DeleteMapping(value = {"/delete/{id}"})
    @ResponseStatus(HttpStatus.OK)
    public void deleteTrip(@PathVariable("id") Integer id) throws
            ResourceNotFoundException {
        tripService.deleteTrip(tripService.getById(id));
    }
}
