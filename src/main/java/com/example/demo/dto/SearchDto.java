package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchDto {
    private String bookTitle;
    private Integer startYear;
    private Integer endYear;
    private String publishingTitle;
    private String lastName;
}
