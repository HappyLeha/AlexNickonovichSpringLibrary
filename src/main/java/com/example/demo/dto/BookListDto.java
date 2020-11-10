package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookListDto implements Comparable<BookListDto> {

    private Long id;
    private String title;
    private LocalDate date;
    private String publishing;
    private Long userId;
    private Long rentId;
    private String name;

    public int compareTo(BookListDto bookListDto) {
        return this.getTitle().compareTo(bookListDto.getTitle());
    }

}
