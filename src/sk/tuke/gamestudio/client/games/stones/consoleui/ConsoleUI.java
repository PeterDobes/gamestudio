package sk.tuke.gamestudio.client.games.stones.consoleui;

import sk.tuke.gamestudio.client.games.ExitException;
import sk.tuke.gamestudio.client.games.GameUserInterface;
import sk.tuke.gamestudio.entity.Score;
import sk.tuke.gamestudio.client.games.stones.StonesPuzzle;
import sk.tuke.gamestudio.client.games.stones.engine.Field;
import sk.tuke.gamestudio.client.games.stones.engine.Space;
import sk.tuke.gamestudio.client.games.stones.engine.Stone;

import java.io.*;
import java.util.Date;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConsoleUI implements GameUserInterface<Field> {
    private Field field;
    private BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

    private String readLine() {
        try {
            return input.readLine();
        } catch (IOException e) {
            return null;
        }
    }

    public void newGame(Field field) {
        this.field = field;
        try {
            scramble();
        } catch (WrongFormatException e) {
            e.printStackTrace();
        }
        StonesPuzzle.getInstance().restartMillis();
        field.setTime(0);
        do {
            update();
            processInput();
            if (field.isSolved()) {
                update();
                System.out.println("Solved!");
                field.setTime(StonesPuzzle.getInstance().getPlayingTime() + field.getTime());
                Score score = new Score(StonesPuzzle.getName(), "stones",
                        (int) ((double) (field.getColumnCount() * field.getRowCount()) /
                                ((double) field.getTime() / 85000d)), new Date());
                StonesPuzzle.getInstance().saveScore(score);
                return;
            }
        } while (true);
    }

    private void scramble() throws WrongFormatException {
        Random num = new Random();
        for (int i = 0; i < 250; i++) {
            int a = num.nextInt(4);
            switch (a) {
                case 0:
                    handleInput("a");
                    break;
                case 1:
                    handleInput("s");
                    break;
                case 2:
                    handleInput("d");
                    break;
                case 3:
                    handleInput("w");
            }
        }
    }

    public void update() {
        System.out.println((StonesPuzzle.getInstance().getPlayingTime() + field.getTime()) + " seconds");
        System.out.println();
        for (int i = 0; i < field.getRowCount(); i++) {
            for (int j = 0; j < field.getColumnCount(); j++) {
                if (field.getStone(i, j).getValue() < field.getStoneCount()) {
                    int value = field.getStone(i, j).getValue();
                    if (value < 10) {
                        System.out.print("  " + value + " ");
                    } else {
                        System.out.print(" " + value + " ");
                    }
                } else {
                    System.out.print("    ");
                }
            }
            System.out.println();
        }
    }

    private void processInput() {
        System.out.println();
        System.out.println("Command: Move~<W, A, S, D>; Exit~<exit>; Scramble~<New>");
        try {
            handleInput(readLine());
        } catch (WrongFormatException e) {
            System.err.println(e.getMessage());
        }
    }

    private void handleInput(String input) throws WrongFormatException {
        boolean inNok = true;
        while (inNok) {
            Pattern p = Pattern.compile("(?i)(exit)?(new)?([wasd]?)");
            Matcher m = p.matcher(input.toLowerCase());
            int sRow = field.getSpace().getRow();
            int sCol = field.getSpace().getCol();
            if (m.matches()) {
                inNok = false;
                switch (m.group(0)) {
                    case "exit":
                        throw new ExitException();
                    case "new":
                        field = new Field(field.getRowCount(), field.getColumnCount());
                        newGame(field);
                        break;
                    case "w":
                        move(sRow, sCol, sRow + 1, sCol);
                        break;
                    case "a":
                        move(sRow, sCol, sRow, sCol + 1);
                        break;
                    case "s":
                        move(sRow, sCol, sRow - 1, sCol);
                        break;
                    case "d":
                        move(sRow, sCol, sRow, sCol - 1);
                    default:
                        break;
                }
            } else {
                throw new WrongFormatException("Wrong input");
            }
        }
    }

    public void move(int sRow, int sCol, int mRow, int mCol) {
        if (mRow > -1 && mRow < field.getRowCount() && mCol > -1 && mCol < field.getColumnCount()) {
            Stone space = field.getStone(sRow, sCol);
            int moved = field.getStone(mRow, mCol).getValue();
            field.getStone(mRow, mCol).setValue(field.getStoneCount());
            space.setValue(moved);
            field.setSpace(new Space(mRow, mCol));
        }
    }
}
