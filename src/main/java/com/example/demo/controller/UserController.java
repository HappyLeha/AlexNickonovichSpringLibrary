package com.example.demo.controller;

import com.example.demo.dto.UserCreateDto;
import com.example.demo.dto.UserDto;
import com.example.demo.dto.UserListDto;
import com.example.demo.entity.Rent;
import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.exceptions.PasswordNotEqualableException;
import com.example.demo.exceptions.ResourceAlreadyCreateException;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.exceptions.ResourceRestrictException;
import com.example.demo.service.UserService;
import com.example.demo.util.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(@RequestBody UserService userService) {
        this.userService = userService;
    }
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public void saveUser(@RequestBody   UserCreateDto userCreateDto) throws ResourceAlreadyCreateException,
            PasswordNotEqualableException {
        System.out.println(userCreateDto.getPassword()+" "+userCreateDto.getRepeatPassword());
        if (!userCreateDto.getPassword().equals(userCreateDto.getRepeatPassword())) throw new PasswordNotEqualableException();
        userService.addNewUser(new User(userCreateDto.getId(),userCreateDto.getLogin(),userCreateDto.getPassword(),
                userCreateDto.getFirstName(),userCreateDto.getLastName(),userCreateDto.getYear(),userCreateDto.getEmail(),
                userCreateDto.getPhone(),userCreateDto.getPhoto(),null, Role.getRoleById(userCreateDto.getRole())));
    }
    @GetMapping(value = {"/{id}"})
    public UserDto findById(@PathVariable("id") int id) throws
            ResourceNotFoundException {
        User user=userService.getById(id);
        return new UserDto(user.getId(),user.getLogin(),user.getPassword(),user.getFirstName(),
                user.getLastName(),user.getYear(),user.getEmail(),user.getPhone(),
                user.getPhoto(),user.getRole().getId());

    }
    @GetMapping(value = {"/all"})
    public List<UserListDto> userList() {
        return Mapper.mapAll(userService.getAllUser(), UserListDto.class);
    }
    @DeleteMapping(value = {"/delete/{id}"})
    @ResponseStatus(HttpStatus.OK)
    public void deleteUser(@PathVariable("id") int id) throws
            ResourceRestrictException {
        userService.deleteUser(id);
    }
    @PutMapping(value = "/edit")
    @ResponseStatus(HttpStatus.OK)
    public void editUser(@RequestBody UserCreateDto userCreateDto) throws ResourceNotFoundException,
            ResourceAlreadyCreateException,PasswordNotEqualableException {
        if (!userCreateDto.getPassword().equals(userCreateDto.getRepeatPassword())) throw new PasswordNotEqualableException();
        userService.editUser(Mapper.map(userCreateDto, User.class));
    }
}
