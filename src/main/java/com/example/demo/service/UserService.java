package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.exceptions.ResourceAlreadyCreateException;
import com.example.demo.exceptions.ResourceNotFoundException;

import java.util.ArrayList;
import java.util.List;

public interface UserService {
    List<User> getAllUser();
    void addNewUser(User user) throws ResourceAlreadyCreateException;
    void deleteUser(User user);
    void editUser(User user,String login) throws ResourceAlreadyCreateException;
    User getById(Integer id) throws ResourceNotFoundException;
    User getByLogin(String login) throws ResourceNotFoundException;
}
