package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCreateDto {

    private String login;
    private String password;
    private String repeatPassword;
    private String firstName;
    private String lastName;
    private Integer year;
    private String email;
    private String phone;
    private String photo;

}
