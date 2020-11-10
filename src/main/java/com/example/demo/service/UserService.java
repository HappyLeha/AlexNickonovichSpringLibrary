package com.example.demo.service;

import com.example.demo.dto.EmailDto;
import com.example.demo.dto.IdDto;
import com.example.demo.entity.User;
import com.example.demo.exceptions.ResourceAlreadyCreateException;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.exceptions.ResourceRestrictException;
import java.security.Principal;
import java.util.List;

public interface UserService {

    List<User> getAllReader();
    List<User> getFreeReader();
    List<User> getAllAdmin();
    void addNewUser(User user) throws ResourceAlreadyCreateException;
    void deleteUser(Long userId) throws ResourceRestrictException;
    void editUser(User user, Principal principal) throws ResourceAlreadyCreateException;
    User getById(Long id) throws ResourceNotFoundException;
    User getByLogin(String login);
    User getProfile(Principal principal);
    List<EmailDto> getEmails(IdDto id);
    void deleteProfile(Principal principal) throws ResourceRestrictException;

}
