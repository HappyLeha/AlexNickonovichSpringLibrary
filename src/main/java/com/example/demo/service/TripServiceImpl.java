package com.example.demo.service;

import com.example.demo.entity.Trip;
import com.example.demo.entity.User;
import com.example.demo.exceptions.ResourceAlreadyCreateException;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.repository.TripRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TripServiceImpl implements TripService {
    private final TripRepository tripRepository;
    private final UserRepository userRepository;
    @Autowired
    public TripServiceImpl(TripRepository tripRepository,UserRepository userRepository) {
        this.tripRepository = tripRepository;
        this.userRepository=userRepository;
    }
    public List<Trip> getAllTrip() {
        return tripRepository.findAll();
    }
    public void addNewTrip(Trip trip) throws ResourceAlreadyCreateException {
        if (tripRepository.existsById(trip.getId())) throw new ResourceAlreadyCreateException();
        tripRepository.save(trip);
    }
    public void deleteTrip(Trip trip){
        for (User user:trip.getUsers()) {
            user.getTrips().remove(trip);
            userRepository.save(user);
        }
        tripRepository.delete(trip);
    }
    public void editTrip(Trip trip,Integer id){
        Trip oldTrip=tripRepository.findTripById(id).get();
        oldTrip.setDateTimeFrom(trip.getDateTimeFrom());
        oldTrip.setDateTimeTo(trip.getDateTimeTo());
        oldTrip.setFrom(trip.getFrom());
        oldTrip.setTo(trip.getTo());
        oldTrip.setCountOfPlaces(trip.getCountOfPlaces());
        oldTrip.setCurrentCountOfPlaces(trip.getCurrentCountOfPlaces());
        oldTrip.setCost(trip.getCost());
        oldTrip.setTransport(trip.getTransport());
        oldTrip.setDriver(trip.getDriver());
        tripRepository.save(oldTrip);
    }
    public Trip getById(Integer id) throws ResourceNotFoundException {
        return tripRepository.findTripById(id)
                .orElseThrow(() -> new ResourceNotFoundException());
    }
}
