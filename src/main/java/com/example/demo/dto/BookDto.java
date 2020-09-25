package com.example.demo.dto;

import com.example.demo.enums.Country;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookDto {
    private Integer id;
    private String title;
    private Integer year;
    private LocalDate date;
    private Integer count;
    private Integer publishing;
    private List<Integer> authors;
}
