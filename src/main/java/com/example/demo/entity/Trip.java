package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Calendar;
import java.util.List;

@Data
@Entity
@Table(name = "trip")
@AllArgsConstructor
@NoArgsConstructor
public class Trip {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private int id;
    @Column(name="datetimefrom")
    private Calendar dateTimeFrom;
    @Column(name="datetimeto")
    private Calendar dateTimeTo;
    @Column(name="fromPlace")
    private String from;
    @Column(name="toPlace")
    private String to;
    @Column(name="countofplaces")
    private int countOfPlaces;
    @Column(name="currentcountofplaces")
    private int currentCountOfPlaces;
    @Column(name="transport")
    private String transport;
    @Column(name="cost")
    private double cost;
    @ManyToOne
    @JoinColumn(name="driver")
    private User driver;
    @ManyToMany(mappedBy = "trips")
    private List<User> users;
}
