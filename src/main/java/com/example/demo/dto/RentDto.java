package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RentDto {
    private Integer id;
    private Integer user;
    private Integer book;
    private LocalDate startDate;
    private LocalDate endDate;
}
