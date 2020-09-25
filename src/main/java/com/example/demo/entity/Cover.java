package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "cover")
@AllArgsConstructor
@NoArgsConstructor
public class Cover {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;
    @Column(name="photo")
    private String photo;
    @Column(name="date")
    private LocalDate date;
    @Column(name="note")
    private String note;
    @ManyToOne
    @JoinColumn(name="book")
    private Book book;
}
