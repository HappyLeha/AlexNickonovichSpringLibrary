package com.example.demo.service;

import com.example.demo.dto.ConnectionDto;
import com.example.demo.entity.Connection;
import com.example.demo.entity.Trip;
import com.example.demo.exceptions.ResourceAlreadyCreateException;
import com.example.demo.exceptions.ResourceNoContentException;
import com.example.demo.exceptions.ResourceNotFoundException;

import java.util.List;

public interface ConnectionService {
    List<Connection> getAllConnection();
    void addNewConnection(Connection connection) throws ResourceAlreadyCreateException, ResourceNoContentException;
    void deleteConnection(Connection connection) throws ResourceNoContentException;
    void isConnection(Connection connection) throws ResourceNotFoundException,ResourceNoContentException;
}
