package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserListDto implements Comparable<UserListDto> {

    private Long id;
    private String login;
    private Long bookId;
    private String book;

    public int compareTo(UserListDto userListDto) {
        return this.getLogin().compareTo(userListDto.getLogin());
    }

}
