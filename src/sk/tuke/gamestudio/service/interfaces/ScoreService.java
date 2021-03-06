package sk.tuke.gamestudio.service.interfaces;

import sk.tuke.gamestudio.entity.Score;

import java.util.List;

public interface ScoreService {
    void addScore(Score score) throws ScoreException;

    List<Score> getBestScoresForGame(String game) throws ScoreException;

    Score getChampion(String game);
}
