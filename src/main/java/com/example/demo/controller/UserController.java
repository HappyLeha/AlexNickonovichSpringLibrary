package com.example.demo.controller;

import com.example.demo.dto.UserDto;
import com.example.demo.entity.User;
import com.example.demo.exceptions.ResourceAlreadyCreateException;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.service.UserService;
import com.example.demo.util.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("users")
class UserController {
    private final UserService userService;
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping(value = {"/all"})
    public List<UserDto> userList() {
        return Mapper.mapAll(userService.getAllUser(), UserDto.class);
    }
    @GetMapping(value = {"/{login}"})
    public UserDto findByLogin(@PathVariable("login") String login) throws
            ResourceNotFoundException {
        return Mapper.map(userService.getByLogin(login), UserDto.class);
    }
    @PutMapping(value = "/edit/{login}")
    @ResponseStatus(HttpStatus.OK)
    public void editUser(@PathVariable("login") String login,@RequestBody
            UserDto userdto) throws ResourceNotFoundException,ResourceAlreadyCreateException {
        userService.getByLogin(login);
        userService.editUser(Mapper.map(userdto, User.class),login);
    }
    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public void saveUser(  @RequestBody UserDto userDto) throws ResourceAlreadyCreateException {
        userService.addNewUser(Mapper.map(userDto, User.class));
    }
    @DeleteMapping(value = {"/delete/{login}"})
    @ResponseStatus(HttpStatus.OK)
    public void deleteUser(@PathVariable("login") String login) throws
            ResourceNotFoundException {
        userService.deleteUser(userService.getByLogin(login));
    }
}