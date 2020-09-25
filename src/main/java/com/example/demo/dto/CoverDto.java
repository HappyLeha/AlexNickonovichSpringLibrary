package com.example.demo.dto;

import com.example.demo.entity.Book;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CoverDto {
    private Integer id;
    private String photo;
    private LocalDate date;
    private String note;
    private Integer book;
}
