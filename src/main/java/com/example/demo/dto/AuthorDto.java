package com.example.demo.dto;

import com.example.demo.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthorDto {
    private Integer id;
    private String firstName;
    private String lastName;
    private Integer year;
    private Gender gender;
    private String note;
}
