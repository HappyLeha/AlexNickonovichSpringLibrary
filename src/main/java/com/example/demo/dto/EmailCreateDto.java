package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailCreateDto {

    private ArrayList<String> emails;
    private String subject;
    private String text;

}
