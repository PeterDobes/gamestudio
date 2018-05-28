package sk.tuke.gamestudio.service;

import sk.tuke.gamestudio.entity.Score;

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

    @Resource(lookup = "jms/achievementQueue")
    private Queue queue;

    @Override
    public void addScore(Score score) {
        entityManager.persist(score);
        if (score.getPoints() > 10000) {
            String text = "New score achievement "+ score.getPlayer();
            context.createProducer().send(queue, context.createTextMessage(text));
        }
    }

    @Override
    public List<Score> getBestScoresForGame(String game) {
        return entityManager.createNamedQuery("Score.getBestScoresForGame", Score.class)
                .setParameter("game", game).setMaxResults(10).getResultList();
    }
}