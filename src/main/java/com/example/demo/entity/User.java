package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "user")
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private Integer id;
    @Column(name="login",unique = true)
    private String login;
    @Column(name="password")
    private int password;
    @OneToMany(mappedBy = "driver",cascade = CascadeType.ALL)
    private List<Trip> drivers;
    @ManyToMany
    @JoinTable(
            name = "user_trip",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "trip_id")
    )
    private List<Trip> trips;
    @OneToMany(mappedBy = "id.user",cascade = CascadeType.ALL)
    private List<Rating> users;
    @OneToMany(mappedBy = "id.login",cascade = CascadeType.ALL)
    private List<Rating> logins;
}
