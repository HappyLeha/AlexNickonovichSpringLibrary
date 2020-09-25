package com.example.demo.dto;

import com.example.demo.enums.Country;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PublishingDto {
    private Integer id;
    private String title;
    private Country country;
    private String address;
    private Integer postIndex;
    private String email;
}
