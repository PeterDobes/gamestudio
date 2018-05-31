package sk.tuke.gamestudio.service;

import sk.tuke.gamestudio.entity.Rating;
import sk.tuke.gamestudio.service.interfaces.RatingService;

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
        try {
            Rating oldRating = entityManager.createNamedQuery("Rating.checkForRating", Rating.class)
                    .setParameter("game", rating.getGame())
                    .setParameter("player", rating.getPlayer())
                    .getSingleResult();
            oldRating.setStars(rating.getStars());
        } catch (NoResultException e) {
            entityManager.persist(rating);
        }
    }

    @Override
    public Double getAverageRating(String game) {
        try {
            return (Double) entityManager.createNamedQuery("Rating.getAverageRating")
                    .setParameter("game", game).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public List<Rating> getAllRatings(String game) {
        return entityManager.createNamedQuery("Rating.getAllRatings", Rating.class)
                .setParameter("game", game).getResultList();
    }
}
