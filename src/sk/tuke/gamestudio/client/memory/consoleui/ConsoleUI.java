package sk.tuke.gamestudio.client.memory.consoleui;

import sk.tuke.gamestudio.client.memory.Memory;
import sk.tuke.gamestudio.client.memory.UserInterface;
import sk.tuke.gamestudio.client.memory.engine.Field;
import sk.tuke.gamestudio.client.memory.engine.Target;
import sk.tuke.gamestudio.entity.Score;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConsoleUI implements UserInterface {

    private static final String blu = "\u001B[36m";
    private static final String blk = "\u001B[30m";
    private static final String red = "\u001B[31m";
    private static final String reset = "\u001B[0m";
    private static final String gre = "\u001B[32m";
    private static final String blue = "\u001B[34m";


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
        tease();
        while (round < 6) {
            update(round);
            processInput();
            if (field.isSolved()) {
                update(round);
                System.out.println("Done!\n\n");
                pause(2);
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
                tease();
                round++;
            }
        }
        System.out.println("\nYou're done!\n");
        Score score = new Score(Memory.getName(), "memory", field.getPoints(), new Date());
        Memory.getInstance().saveScore(score);
    }

    private void tease() {
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
                    if (field.getTile(i - 1, j) instanceof Target) {
                        System.out.print(blue + " X " + reset);
                    } else {
                        System.out.print(blk + " X " + reset);
                    }
                }
            }
            System.out.println();
        }
        System.out.println("\n\n\n\n");
        pause(3);
    }

    private void pause(int time) {
        try {
            TimeUnit.SECONDS.sleep(time);
        } catch (InterruptedException e) {
            System.err.println("It's better for you if you don't interrupt");
        }
    }

    @Override
    public void update(int round) {
        System.out.println("\n\n\n\nRound " + round + "/5\n");
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
                            System.out.print(blk + " X " + reset);
                            break;
                        case OPEN:
                            if (field.getTile(i - 1, j) instanceof Target) {
                                System.out.print(blue + " X " + reset);
                            } else {
                                System.out.print(red + " X " + reset);
                            }
                            break;
                    }
                }
            }
            System.out.println();
        }
        System.out.println("\n\n\n\n");
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
