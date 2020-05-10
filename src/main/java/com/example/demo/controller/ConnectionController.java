package com.example.demo.controller;

import com.example.demo.dto.ConnectionDto;
import com.example.demo.dto.TripDto;
import com.example.demo.entity.Connection;
import com.example.demo.entity.Trip;
import com.example.demo.exceptions.ResourceAlreadyCreateException;
import com.example.demo.exceptions.ResourceNoContentException;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.service.ConnectionService;
import com.example.demo.service.TripService;
import com.example.demo.service.UserService;
import com.example.demo.util.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("connections")
public class ConnectionController {
    private final ConnectionService connectionService;
    private final UserService userService;
    @Autowired
    public ConnectionController(ConnectionService connectionService,UserService userService) {
        this.connectionService = connectionService;
        this.userService=userService;
    }
    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public void saveConnection(@RequestBody ConnectionDto connectionDto) throws ResourceAlreadyCreateException,
            ResourceNotFoundException, ResourceNoContentException {
        Connection connection=new Connection(userService.getByLogin(connectionDto.getUser()).getId(),
                connectionDto.getTrip());
        connectionService.addNewConnection(connection);
    }
    @GetMapping(value = {"/all"})
    public List<ConnectionDto> connectionList() throws ResourceNotFoundException{
        List<Connection> connections=connectionService.getAllConnection();
        ArrayList<ConnectionDto> connectionDtos=new ArrayList<>();
        for (Connection connection:connections) {
            connectionDtos.add(new ConnectionDto(userService.getById(connection.getUser()).getLogin(),connection.getTrip()));
        }
        return connectionDtos;
    }
    @GetMapping(value = {"/head"})
    @ResponseStatus(HttpStatus.OK)
    public void isConnection (@RequestBody ConnectionDto connectionDto) throws
            ResourceNoContentException,ResourceNotFoundException {
        Connection connection=new Connection(userService.getByLogin(connectionDto.getUser()).getId(),
                connectionDto.getTrip());
        connectionService.isConnection(connection);
    }
    @DeleteMapping(value = {"/delete"})
    @ResponseStatus(HttpStatus.OK)
    public void deleteTrip(@RequestBody ConnectionDto connectionDto) throws
            ResourceNotFoundException,ResourceNoContentException {
        Connection connection=new Connection(userService.getByLogin(connectionDto.getUser()).getId(),
                connectionDto.getTrip());
        connectionService.deleteConnection(connection);
    }


}
