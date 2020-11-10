package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FreeBookListDto implements Comparable<FreeBookListDto> {

    private Long id;
    private String title;
    private LocalDate date;
    private String publishing;
    private Long rentId;

    public int compareTo(FreeBookListDto freeBookListDto) {
        return this.getTitle().compareTo(freeBookListDto.getTitle());
    }
}
