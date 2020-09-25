package com.example.demo.dto;

import com.example.demo.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthorListDto {
    private Integer id;
    private String firstName;
    private String lastName;
    private Integer year;
}
