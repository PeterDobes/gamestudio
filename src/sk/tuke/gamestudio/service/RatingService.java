package sk.tuke.gamestudio.service;

import sk.tuke.gamestudio.entity.Rating;

import java.util.List;

public interface RatingService {
    void setRating(Rating rating);

    String getAverageRating(String game);

    List<Rating> getAllRatings(String game);
}
