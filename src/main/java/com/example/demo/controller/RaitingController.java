package com.example.demo.controller;

import com.example.demo.dto.RatingDto;
import com.example.demo.entity.Rating;
import com.example.demo.entity.RatingId;
import com.example.demo.entity.User;
import com.example.demo.exceptions.ResourceNoContentException;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.service.RatingService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("ratings")
public class RaitingController {
    private final RatingService ratingService;
    private final UserService userService;
    @Autowired
    public RaitingController(RatingService ratingService, UserService userService) {
        this.ratingService = ratingService;
        this.userService=userService;
    }
    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public void saveRaiting(@RequestBody RatingDto ratingDto) throws ResourceNoContentException {
        try {
            User user = userService.getByLogin(ratingDto.getUser());
            User login = userService.getByLogin(ratingDto.getLogin());
            ratingService.addNewRating(new Rating(new RatingId(user,login),ratingDto.getRating()));
        }
        catch (ResourceNotFoundException e) {
            throw new ResourceNoContentException();
        }

    }
    @GetMapping(value = {"/all"})
    public List<RatingDto> ratingList(){
        List<Rating> ratings=ratingService.getAllRating();
        ArrayList<RatingDto> ratingDtos=new ArrayList<>();
        for (Rating rating:ratings) {
            ratingDtos.add(new RatingDto(rating.getId().getUser().getLogin(),
                    rating.getId().getLogin().getLogin(),rating.getRating()));
        }
        return ratingDtos;
    }
}
