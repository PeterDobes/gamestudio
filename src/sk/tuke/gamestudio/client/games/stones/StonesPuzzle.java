package sk.tuke.gamestudio.client.games.stones;

import sk.tuke.gamestudio.client.clientServices.ScoreRestServiceClient;
import sk.tuke.gamestudio.entity.Score;
import sk.tuke.gamestudio.client.games.stones.consoleui.ConsoleUI;
import sk.tuke.gamestudio.client.games.stones.engine.Field;

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
        Field field = new Field(3,3);
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
