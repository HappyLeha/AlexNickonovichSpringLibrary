package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailDto implements Comparable<EmailDto> {

    private String login;
    private String email;

    public int compareTo(EmailDto emailDto) {
        return this.getLogin().compareTo(emailDto.getLogin());
    }

}
