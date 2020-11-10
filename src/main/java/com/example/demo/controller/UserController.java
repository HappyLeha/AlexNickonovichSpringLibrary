package com.example.demo.controller;

import com.example.demo.dto.*;
import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.exceptions.*;
import com.example.demo.service.UserService;
import com.example.demo.util.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("user")

public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void saveUser(@RequestBody UserCreateDto userCreateDto)
            throws ResourceAlreadyCreateException, PasswordNotEqualableException {
        if (!userCreateDto.getPassword().equals(userCreateDto.getRepeatPassword())) {
            throw new PasswordNotEqualableException();
        }
        userService.addNewUser(new User(0L,userCreateDto.getLogin(),userCreateDto.getPassword(),
                userCreateDto.getFirstName(),userCreateDto.getLastName(),userCreateDto.getYear(),
                userCreateDto.getEmail(), userCreateDto.getPhone(),userCreateDto.getPhoto(),
                null, new Role(2L,"ROLE_READER",null)));
    }

    @GetMapping(value = {"/{id}"})
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public UserDto findById(@PathVariable("id") long id) throws ResourceNotFoundException {
        User user=userService.getById(id);
        return new UserDto(user.getId(),user.getLogin(),user.getFirstName(),
                user.getLastName(),user.getYear(),user.getEmail(),user.getPhone(),
                user.getPhoto());
    }

    @GetMapping(value = {"/profile"})
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_READER')")
    public UserDto getProfile (Principal principal) {
        User user=userService.getProfile(principal);
        return new UserDto(user.getId(),user.getLogin(),user.getFirstName(),
                user.getLastName(),user.getYear(),user.getEmail(),user.getPhone(),
                user.getPhoto());
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<UserListDto> userList() {
        List<User> users=userService.getAllReader();
        List<UserListDto> results=new ArrayList<>();
        for (User user:users) {
            UserListDto element=new UserListDto();
            element.setId(user.getId());
            element.setLogin(user.getLogin());
            if (user.getRent()!=null) {
                element.setBook(user.getRent().getBook().getTitle());
                element.setBookId(user.getRent().getBook().getId());
            }
            results.add(element);
        }
        Collections.sort(results);
        return results;
    }

    @GetMapping(value = {"/free"})
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<UserListDto> userFreeList() {
        List<UserListDto> results=Mapper.mapAll(userService.getFreeReader(), UserListDto.class);
        Collections.sort(results);
        return results;
    }

    @DeleteMapping(value = {"/{id}"})
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void deleteUser(@PathVariable("id") long id) throws ResourceRestrictException {
        userService.deleteUser(id);
    }
    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_READER')")
    public void deleteProfile(Principal principal) throws ResourceRestrictException {
        userService.deleteProfile(principal);
    }
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_READER')")
    public void editUser(@RequestBody UserCreateDto userCreateDto,Principal principal)
            throws ResourceAlreadyCreateException,PasswordNotEqualableException {
        String password=userCreateDto.getPassword();
        String repeatPassword=userCreateDto.getRepeatPassword();
        if (password!=null&&repeatPassword==null||password==null&&repeatPassword!=null) {
            throw new PasswordNotEqualableException();
        }
        if (password==repeatPassword||password.equals(repeatPassword)) {
            userService.editUser(new User(0L,userCreateDto.getLogin(),userCreateDto.getPassword(),
                    userCreateDto.getFirstName(), userCreateDto.getLastName(),
                    userCreateDto.getYear(),userCreateDto.getEmail(),userCreateDto.getPhone(),
                    userCreateDto.getPhoto(),null,null), principal);
        }
        else throw new PasswordNotEqualableException();
    }

    @PostMapping(value = {"/email"})
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<EmailDto> getEmails(@RequestBody IdDto id) {
        return userService.getEmails(id);
    }

}
