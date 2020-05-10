package com.example.demo.service;

import com.example.demo.entity.Rating;
import com.example.demo.repository.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class RatingServiceImpl implements RatingService {
    private final RatingRepository ratingRepository;
    @Autowired
    public RatingServiceImpl(RatingRepository ratingRepository) {
        this.ratingRepository=ratingRepository;
    }
    public void addNewRating(Rating rating) {
       if (ratingRepository.existsById(rating.getId())) {
           Rating oldRating=ratingRepository.findRatingById(rating.getId()).get();
           oldRating.setRating(rating.getRating());
           ratingRepository.save(oldRating);
       }
       else {
           ratingRepository.save(rating);
       }
    }
    public List<Rating> getAllRating() {
        return ratingRepository.findAll();
    }
}
