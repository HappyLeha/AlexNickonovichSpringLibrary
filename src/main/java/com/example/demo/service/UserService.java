package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.exceptions.ResourceAlreadyCreateException;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.exceptions.ResourceRestrictException;

import java.util.List;

public interface UserService {
    List<User> getAllUser();
    void addNewUser(User user) throws ResourceAlreadyCreateException;
    void deleteUser(Integer userId) throws ResourceRestrictException;
    void editUser(User user) throws ResourceAlreadyCreateException,ResourceNotFoundException;
    User getById(Integer id) throws ResourceNotFoundException;

}
