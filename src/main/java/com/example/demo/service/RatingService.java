package com.example.demo.service;

import com.example.demo.entity.Rating;

import java.util.List;

public interface RatingService {
    List<Rating> getAllRating();
    void addNewRating(Rating rating);
}
