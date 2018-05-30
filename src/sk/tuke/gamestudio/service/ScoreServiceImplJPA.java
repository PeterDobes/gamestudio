package sk.tuke.gamestudio.service;

import sk.tuke.gamestudio.entity.Score;
import sk.tuke.gamestudio.service.interfaces.ScoreService;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jms.JMSContext;
import javax.jms.Queue;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
        if (!getChampion(score.getGame()).isEmpty()) {
            if (score.getPoints() > getChampion(score.getGame()).get(0).getPoints()) {
                String text = "New champion " + score.getPlayer();
                context.createProducer().send(queue, context.createTextMessage(text));
            }
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
    public List<Score> getChampion(String game) {
        return entityManager.createNamedQuery("Score.getBestScoresForGame", Score.class)
                .setParameter("game", game).setMaxResults(1).getResultList();
    }
}