package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "rent")
@AllArgsConstructor
@NoArgsConstructor
public class Rent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;
    @OneToOne
    @JoinColumn(name = "user")
    private User user;
    @ManyToOne
    @JoinColumn(name="book")
    private Book book;
    @Column(name="startdate")
    private LocalDate startDate;
    @Column(name="enddate")
    private LocalDate endDate;
}
