package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RentDto {

    private String user;
    private String book;
    private LocalDate startDate;
    private LocalDate endDate;

}
