package sk.tuke.gamestudio.client.minesweeper;

import sk.tuke.gamestudio.client.ScoreRestServiceClient;
import sk.tuke.gamestudio.entity.Score;
import sk.tuke.gamestudio.client.minesweeper.consoleui.ConsoleUI;
import sk.tuke.gamestudio.client.minesweeper.core.Field;


public class Minesweeper {
    private ScoreRestServiceClient scoreService = new ScoreRestServiceClient();

    private static String name;
    private ConsoleUI userInterface;
    private static long startMillis;
    private static Minesweeper instance;
    private Settings setting;

    public Minesweeper(String gName) {

        name = gName;
        instance = this;
        userInterface = new ConsoleUI();
        setSetting(Settings.load());
        
        Field field = new Field(setting.getRowCount(), setting.getColumnCount(),setting.getMineCount());
        startMillis = System.currentTimeMillis();
        userInterface.newGameStarted(field);
    }

    public static void main(String[] args) {
        new Minesweeper(name);
    }

    public int getPlayingSeconds() {
        return (int) ((System.currentTimeMillis() - startMillis) / 1000);
    }

    public static String getName() {
        return name;
    }

    public static Minesweeper getInstance() {
        return instance;
    }

    public Settings getSetting() {
        return setting;
    }

    public void setSetting(Settings setting) {
        this.setting = setting;
        setting.save();
    }

    public void saveScore(Score score) {
        scoreService.addScore(score);
    }
}
