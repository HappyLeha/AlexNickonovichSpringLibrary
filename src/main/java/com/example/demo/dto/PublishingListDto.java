package com.example.demo.dto;

import com.example.demo.enums.Country;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PublishingListDto {
    private Integer id;
    private String title;
    private Country country;
    private String address;
}
