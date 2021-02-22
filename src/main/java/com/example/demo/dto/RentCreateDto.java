package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RentCreateDto {

    private Long id;
    private Long user;
    private Long book;
    private LocalDate startDate;
    private LocalDate endDate;

}
