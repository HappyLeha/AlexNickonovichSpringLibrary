package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private Long id;
    private String login;
    private String firstName;
    private String lastName;
    private Integer year;
    private String email;
    private String phone;
    private String photo;

}
