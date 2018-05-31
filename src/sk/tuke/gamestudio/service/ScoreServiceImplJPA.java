package sk.tuke.gamestudio.service;

import sk.tuke.gamestudio.entity.Champion;
import sk.tuke.gamestudio.entity.Score;
import sk.tuke.gamestudio.service.interfaces.ScoreService;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jms.JMSContext;
import javax.jms.Queue;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.Date;
import java.util.List;

@Stateless
public class ScoreServiceImplJPA implements ScoreService {
    @PersistenceContext
    private EntityManager entityManager;

    @Inject
    private JMSContext context;

    @Resource(lookup = "jms/championQueue")
    private Queue queue;

    @Override
    public void addScore(Score score) {
        Score championScore = getChampion(score.getGame());
        Champion champion;
        if (championScore == null) {
            champion = new Champion(score.getPlayer(),
                    score.getGame(), new Date(), "-first champion-");
            entityManager.persist(champion);
        } else if (score.getPoints() > championScore.getPoints()) {
            champion = new Champion(score.getPlayer(),
                    score.getGame(), new Date(), championScore.getPlayer());
            entityManager.persist(champion);
        }
        entityManager.persist(score);

    }
//
//    @Override
//    public void addScore(Score score) {
//        entityManager.persist(score);
//    }

    @Override
    public List<Score> getBestScoresForGame(String game) {
        return entityManager.createNamedQuery("Score.getBestScoresForGame", Score.class)
                .setParameter("game", game).setMaxResults(10).getResultList();
    }

    @Override
    public Score getChampion(String game) {
        List<Score> scores = getBestScoresForGame(game);
        if(scores.size() > 0)
            return scores.get(0);
        return null;
//        try {
//            return entityManager.createNamedQuery("Score.getBestScoresForGame", Score.class)
//                    .setParameter("game", game).getSingleResult();
//        } catch (NoResultException e) {
//            return null;
//        }
    }
}