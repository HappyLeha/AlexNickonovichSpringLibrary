package com.example.demo.entity;

//import com.example.demo.enums.Country;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private Integer id;
    @Column(name="title")
    private String title;
    @Column(name="year")
    private Integer year;
    @Column(name="date")
    private LocalDate date;
    @Column(name="count")
    private Integer count;
    @ManyToMany
    @JoinTable(
            name = "author_book",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id")
    )
    private List<Author> authors;
    @OneToMany(mappedBy="book")
    private List<Rent> rents;
    @ManyToOne
    @JoinColumn(name="publishing")
    private Publishing publishing;
    @OneToMany(mappedBy="book")
    private List<Cover> covers;
}
