package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@Table(name = "book")
@AllArgsConstructor
@NoArgsConstructor
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="title")
    private String title;

    @Column(name="date")
    private LocalDate date;

    @Column(name="count")
    private Integer count;

    @Column(name="authors")
    private String authors;

    @Column(name="publishing")
    private String publishing;

    @OneToMany(mappedBy="book")
    @ToString.Exclude
    private List<Rent> rents;

}
