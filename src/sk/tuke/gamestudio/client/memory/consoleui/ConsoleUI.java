package sk.tuke.gamestudio.client.memory.consoleui;

import sk.tuke.gamestudio.client.memory.Memory;
import sk.tuke.gamestudio.client.memory.UserInterface;
import sk.tuke.gamestudio.client.memory.engine.Field;
import sk.tuke.gamestudio.client.memory.engine.Target;
import sk.tuke.gamestudio.client.memory.engine.Tile;
import sk.tuke.gamestudio.entity.Score;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConsoleUI implements UserInterface {

    private static final String cya = "\u001B[36m";
    private static final String red = "\u001B[31m";
    private static final String reset = "\u001B[0m";
    private static final String gre = "\u001B[32m";
    private static final String blue = "\u001B[34m";
    private static final String whi = "\u001B[37m";


    private Field field;
    private BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

    private String readLine() {
        try {
            return input.readLine();
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    public void newGame(Field newField) {
        int round = 1;
        field = newField;
        System.out.println(gre + "Get ready!" + reset);
        pause(1);
        countdown();
        showHint();
        while (round < 6) {
            System.out.println("\n\n\n\nRound " + round + "/5\n");
            update();
            processInput();
            if (field.isSolved()) {
                System.out.println(gre + "Done!\n\n" + reset);
                reveal();
                update();
                pause(2);
                round++;
                if (round < 6) {
                    switch (field.getErrors()) {
                        case 0:
                            if (field.getTargetCount() % 2 == 0) {
                                field.setRowCount(field.getRowCount() + 1);
                            } else {
                                field.setColumnCount(field.getColumnCount() + 1);
                            }
                            field.setTargetCount(field.getTargetCount() + 1);
                            System.out.println(gre + "Level up: " + field.getTargetCount() + reset);
                            break;
                        case 1:
                            System.out.println(gre + "Level: " + field.getTargetCount() + reset);
                            break;
                        default:
                            if (field.getTargetCount() % 2 == 1) {
                                field.setRowCount(field.getRowCount() - 1);
                            } else {
                                field.setColumnCount(field.getColumnCount() - 1);
                            }
                            field.setTargetCount(field.getTargetCount() - 1);
                            System.out.println(gre + "Level down: " + field.getTargetCount() + reset);
                    }
                    field = new Field(field.getRowCount(), field.getColumnCount(),
                            field.getTargetCount(), field.getPoints());
                    countdown();
                    showHint();
                }
            }
        }
        System.out.println("\nFinished!\n");
        Score score = new Score(Memory.getName(), "memory", field.getPoints(), new Date());
        Memory.getInstance().saveScore(score);
    }

    private void countdown() {
        System.out.println(gre + "3" + reset);
        pause(1);
        System.out.println(gre + "2" + reset);
        pause(1);
        System.out.println(gre + "1" + reset);
        pause(1);
    }

    @Override
    public void update() {
        char[] rows = {' ', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P'};
        for (int i = 0; i <= field.getRowCount(); i++) {
            if (i == 1) {
                System.out.println();
            }
            System.out.print(rows[i] + "  ");
            for (int j = 0; j < field.getColumnCount(); j++) {
                if (i == 0) {
                    if (j < 10) {
                        System.out.print(" " + j + " ");
                    } else {
                        System.out.print(" " + j);
                    }
                } else {
                    switch (field.getTile(i - 1, j).getState()) {
                        case CLOSED:
                            System.out.print(whi + " X " + reset);
                            break;
                        case OPEN:
                            if (field.getTile(i - 1, j) instanceof Target) {
                                System.out.print(blue + " X " + reset);
                            } else {
                                System.out.print(red + " X " + reset);
                            }
                            break;
                        case HIGHLIGHT:
                            System.out.print(cya + " X " + reset);
                    }
                }
            }
            System.out.println();
        }
        System.out.println("\n\n\n");
    }

    private void showHint() {
        hint();
        update();
        hint();
        pause(4);
    }

    private void hint() {
        for (int i = 0; i < field.getRowCount(); i++) {
            for (int j = 0; j < field.getColumnCount(); j++) {
                if (field.getTile(i, j) instanceof Target) {
                    field.swtichState(i, j);
                }
            }
        }
    }

    private void reveal() {
        for (int i = 0; i < field.getRowCount(); i++) {
            for (int j = 0; j < field.getColumnCount(); j++) {
                Tile tile = field.getTile(i, j);
                if (tile instanceof Target && tile.getState().equals(Tile.State.CLOSED)) {
                    tile.setState(Tile.State.HIGHLIGHT);
                }
            }
        }
    }

    private void pause(int time) {
        try {
            TimeUnit.SECONDS.sleep(time);
        } catch (InterruptedException e) {
            System.err.println("It's better for you if you don't interrupt");
        }
    }

    private void processInput() {
        System.out.println();
        System.out.println("Highlight squares");
        try {
            handleInput(readLine());
        } catch (WrongFormatException e) {
            System.err.println(e.getMessage());
        }
    }

    private void handleInput(String input) throws WrongFormatException {
        boolean inNok = true;
        while (inNok) {
            System.out.println();
            Pattern p = Pattern.compile("(?i)([X])?([A-I])([0-8])?");
            Matcher m = p.matcher(input.toLowerCase());
            if (m.matches()) {
                inNok = false;
                if (m.group(0).charAt(0) == 'x') {
                    System.exit(0);
                } else {
                    int rowO = m.group(0).charAt(0) - 97;
                    if (m.group(0).length() == 2) {
                        int colO = m.group(0).charAt(1) - 48;
                        if (rowO < field.getRowCount() && colO < field.getColumnCount()) {
                            field.openTile(rowO, colO);
                        } else {
                            System.err.println("Check coordinates");
                        }
                    } else {
                        System.err.println("Check coordinates");
                    }
                }
            } else {
                throw new WrongFormatException("Wrong Format Exception");
            }
        }
    }
}