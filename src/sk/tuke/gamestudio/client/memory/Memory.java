package sk.tuke.gamestudio.client.memory;

import sk.tuke.gamestudio.client.ScoreRestServiceClient;
import sk.tuke.gamestudio.client.memory.consoleui.ConsoleUI;
import sk.tuke.gamestudio.client.memory.engine.Field;
import sk.tuke.gamestudio.entity.Score;

public class Memory {
    private ScoreRestServiceClient scoreService = new ScoreRestServiceClient();

    private static Memory instance;
    private static String name;
    private ConsoleUI userInterface;

    public Memory(String gName) {
        userInterface = new ConsoleUI();
        name = gName;
        instance = this;

        Field field = new Field(5, 5, 5, 0);
        userInterface.newGame(field);
    }

    public static void main(String[] args) {
        new Memory(name);
    }

    public static String getName() {
        return name;
    }

    public static void setName(String name) {
        Memory.name = name;
    }

    public void saveScore(Score score) {
        scoreService.addScore(score);
    }
    public static Memory getInstance() {
        return instance;
    }

}
