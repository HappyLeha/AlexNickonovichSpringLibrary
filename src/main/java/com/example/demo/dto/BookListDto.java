package com.example.demo.dto;

import com.example.demo.enums.Country;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookListDto {
    private Integer id;
    private String title;
    private LocalDate date;
    private Integer count;
    private Integer publishing;
}
