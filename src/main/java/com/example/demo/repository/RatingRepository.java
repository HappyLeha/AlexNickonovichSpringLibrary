package com.example.demo.repository;

import com.example.demo.entity.Rating;
import com.example.demo.entity.RatingId;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface RatingRepository extends CrudRepository<Rating, RatingId> {
    List<Rating> findAll();
    Optional<Rating> findRatingById(RatingId ratingId);
}
