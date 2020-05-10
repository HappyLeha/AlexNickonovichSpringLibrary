package com.example.demo.service;

import com.example.demo.controller.ResourceNotFound;
import com.example.demo.dto.ConnectionDto;
import com.example.demo.entity.Connection;
import com.example.demo.entity.Trip;
import com.example.demo.entity.User;
import com.example.demo.exceptions.ResourceAlreadyCreateException;
import com.example.demo.exceptions.ResourceNoContentException;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.repository.TripRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ConnectionServiceImpl implements ConnectionService {
    private final UserRepository userRepository;
    private final TripRepository tripRepository;
    @Autowired
    public ConnectionServiceImpl(UserRepository userRepository,TripRepository tripRepository) {
        this.userRepository = userRepository;
        this.tripRepository=tripRepository;
    }
    public List<Connection> getAllConnection() {
        List<User> users=userRepository.findAll();
        ArrayList<Connection> connections=new ArrayList<>();
        for (User user:users) {
            for (Trip trip:user.getTrips()) {
                connections.add(new Connection(user.getId(),trip.getId()));
            }
        }
        return connections;
    }
    public void addNewConnection(Connection connection) throws ResourceAlreadyCreateException, ResourceNoContentException {
        if (userRepository.findUserById(connection.getUser()).get().getTrips().
                stream().filter(i->i.getId()==connection.getTrip()).count()==1) throw new ResourceAlreadyCreateException();
        if (!tripRepository.findTripById(connection.getTrip()).isPresent()) throw new ResourceNoContentException();
        Trip trip=tripRepository.findTripById(connection.getTrip()).get();
        User user=userRepository.findUserById(connection.getUser()).get();
        user.getTrips().add(trip);
        userRepository.save(user);
    }
    public void deleteConnection(Connection connection) throws ResourceNoContentException{
        if (!tripRepository.findTripById(connection.getTrip()).isPresent()) throw new ResourceNoContentException();
        Trip trip=tripRepository.findTripById(connection.getTrip()).get();
        User user=userRepository.findUserById(connection.getUser()).get();
        user.getTrips().remove(trip);
        userRepository.save(user);
    }
    public void isConnection(Connection connection) throws ResourceNotFoundException,ResourceNoContentException {
        if (!tripRepository.findTripById(connection.getTrip()).isPresent()) throw new ResourceNoContentException();
        Trip trip=tripRepository.findTripById(connection.getTrip()).get();
        User user=userRepository.findUserById(connection.getUser()).get();
        if (!(trip.getUsers().contains(user)&&user.getTrips().contains(trip))) throw new ResourceNotFoundException();
    }
}

