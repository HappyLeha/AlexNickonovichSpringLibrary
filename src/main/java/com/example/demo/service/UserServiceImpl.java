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
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final TripRepository tripRepository;
    @Autowired
    public UserServiceImpl(UserRepository userRepository,TripRepository tripRepository) {
        this.userRepository = userRepository;
        this.tripRepository=tripRepository;
    }
    public List<User> getAllUser() {
        return userRepository.findAll();
    }
    public void addNewUser(User user) throws ResourceAlreadyCreateException{
        if (getAllUser().stream().filter(i->i.getLogin().equals(user.getLogin())).count()==1) throw  new ResourceAlreadyCreateException();
        else {
            userRepository.save(user);
        }
      
    }
    public void deleteUser(User user){
        for (Trip trip:user.getDrivers()) {
            for (User userTrip:trip.getUsers()) {
                userTrip.getTrips().remove(trip);
                userRepository.save(userTrip);
            }
        }
        for (Trip trip:user.getTrips()) {
            trip.getUsers().remove(user);
            tripRepository.save(trip);
        }
        userRepository.delete(user);
    }
    public void editUser(User user,String login) throws ResourceAlreadyCreateException {
        boolean eq=!user.getLogin().equals(login);
        boolean exist=getAllUser().stream().filter(i->i.getLogin().equals(user.getLogin())).count()==1;
        if (eq&&exist) throw new ResourceAlreadyCreateException();
        User oldUser=getAllUser().stream().filter(i->i.getLogin().equals(login)).findFirst().get();
        oldUser.setLogin(user.getLogin());
        oldUser.setPassword(user.getPassword());
        userRepository.save(oldUser);
    }
    public User getById(Integer id) throws ResourceNotFoundException {
        return userRepository.findUserById(id)
                .orElseThrow(() -> new ResourceNotFoundException());
    }
    public User getByLogin(String login) throws ResourceNotFoundException {
       Optional<User> user=getAllUser().stream().filter(i->i.getLogin().equals(login)).findFirst();
       if (user.isPresent()) return user.get();
       else throw new ResourceNotFoundException();
    }
}
