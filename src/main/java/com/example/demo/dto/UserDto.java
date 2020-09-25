package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private Integer id;
    private String login;
    private String password;
    private String firstName;
    private String lastName;
    private Integer year;
    private String email;
    private String phone;
    private String photo;
    private Integer role;
}
