package sk.tuke.gamestudio.client.stones;

import sk.tuke.gamestudio.client.ScoreRestServiceClient;
import sk.tuke.gamestudio.entity.Score;
import sk.tuke.gamestudio.service.ScoreException;
import sk.tuke.gamestudio.service.ScoreService;
import sk.tuke.gamestudio.client.stones.consoleui.ConsoleUI;
import sk.tuke.gamestudio.client.stones.engine.Field;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class StonesPuzzle {

    private ScoreRestServiceClient scoreService = new ScoreRestServiceClient();

    private static String name;
    private ConsoleUI userInterface;
    public static long startMillis;
    private static StonesPuzzle instance;

    public StonesPuzzle(String gName) {
        name = gName;
        instance = this;
        userInterface = new ConsoleUI();
        Field field = new Field(1,2);
        userInterface.newGame(field);
    }

    public static void main(String[] args) {
        new StonesPuzzle(name);
    }

    public int getPlayingTime() {
        return (int) ((System.currentTimeMillis() - startMillis) / 1000);
    }

    public void restartMillis() {
        startMillis = System.currentTimeMillis();
    }

    public static StonesPuzzle getInstance() {
        return instance;
    }

    public void saveScore(Score score) {
        scoreService.addScore(score);
    }

    public static String getName() {
        return name;
    }
}
