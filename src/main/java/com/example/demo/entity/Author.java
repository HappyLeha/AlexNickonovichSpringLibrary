package com.example.demo.entity;

import com.example.demo.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "author")
@AllArgsConstructor
@NoArgsConstructor
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;
    @Column(name="firstname")
    private String firstName;
    @Column(name="lastname")
    private String lastName;
    @Column(name="year")
    private Integer year;
    @Column(name="gender")
    private Gender gender;
    @Column(name="note")
    private String note;
    @ManyToMany(mappedBy = "authors")
    private List<Book> books;
}
