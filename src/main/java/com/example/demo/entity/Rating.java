package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Table(name = "rating")
@AllArgsConstructor
@NoArgsConstructor
public class Rating {
    @EmbeddedId
    private RatingId id;
    @Column(name="rating")
    private int rating;
}
