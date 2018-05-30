package sk.tuke.gamestudio.service;

import sk.tuke.gamestudio.entity.Rating;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class RatingServiceImplJPA implements RatingService {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void setRating(Rating rating) {
        Rating oldRating;
        try {
            oldRating = entityManager.createNamedQuery("Rating.checkForRating", Rating.class)
                    .setParameter("game", rating.getGame())
                    .setParameter("player", rating.getPlayer())
                    .getSingleResult();
            oldRating.setStars(rating.getStars());
        } catch (NoResultException e) {
            entityManager.persist(rating);
        }
    }

    @Override
    public String getAverageRating(String game) {
        String rating = "-not rated yet-";
        try {
            rating = entityManager.createNamedQuery("Rating.getAverageRating")
                    .setParameter("game", game).getSingleResult().toString().substring(0, 3);
        } catch (Exception e) {
            return rating;
        }
        return rating + " stars";
    }

    @Override
    public List<Rating> getAllRatings(String game) {
        return entityManager.createNamedQuery("Rating.getAllRatings", Rating.class)
                .setParameter("game", game).getResultList();
    }
}
