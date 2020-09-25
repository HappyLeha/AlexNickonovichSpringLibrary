package com.example.demo.entity;

import com.example.demo.enums.Country;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "publishing")
@AllArgsConstructor
@NoArgsConstructor
public class Publishing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;
    @Column(name="title")
    private String title;
    @Column(name="country")
    private Country country;
    @Column(name="address")
    private String address;
    @Column(name="postindex")
    private Integer postIndex;
    @Column(name="email")
    private String email;
    @OneToMany(mappedBy="publishing")
    private List<Book> books;
}
