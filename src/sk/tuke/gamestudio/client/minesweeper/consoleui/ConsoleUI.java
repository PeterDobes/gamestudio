package sk.tuke.gamestudio.client.minesweeper.consoleui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import sk.tuke.gamestudio.entity.Score;
import sk.tuke.gamestudio.client.minesweeper.Minesweeper;
import sk.tuke.gamestudio.client.minesweeper.UserInterface;
import sk.tuke.gamestudio.client.minesweeper.core.*;

/**
 * Console user interface.
 */
public class ConsoleUI implements UserInterface {
    /**
     * Playing field.
     */
    private Field field;

    /**
     * Input reader.
     */
    private BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

    /**
     * Reads line of text from the reader.
     *
     * @return line as a string
     */
    private String readLine() {
        try {
            return input.readLine();
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * Starts the game.
     *
     * @param field field of mines and clues
     */
    @Override
    public void newGameStarted(Field field) {
        this.field = field;
        do {
            update();
            processInput();
            if (field.getState() == GameState.SOLVED) {
                update();
                System.out.println("Solved!");
                int time = Minesweeper.getInstance().getPlayingSeconds();
                Score score = new Score(Minesweeper.getName(), "minesweeper",
                        (int)((double)(field.getColumnCount() * field.getRowCount() *
                                field.getMineCount()) / ((double)time / 2000d)),
                        new Date());
                Minesweeper.getInstance().saveScore(score);
                return;
            } else if (field.getState() == GameState.FAILED) {
                update();
                System.out.println("Failed");
                return;
            }
        } while (true);
    }

    /**
     * Updates user interface - prints the field.
     */
    @Override
    public void update() {
        System.out.println("\n" + Minesweeper.getInstance().getPlayingSeconds() + " seconds");
        System.out.println("Remaining mines: " + field.getRemainingMineCount() + "\n");
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
                            System.out.print(" _ ");
                            break;
                        case OPEN:
                            if (field.getTile(i - 1, j) instanceof Mine) {
                                System.out.print(" X ");
                            } else {
                                System.out.print(" " + ((Clue) field.getTile(i - 1, j)).getValue() + " ");
                            }
                            break;
                        case MARKED:
                            System.out.print(" M ");
                            break;
                    }
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    /**
     * Processes user input.
     * Reads line from console and does the action on a playing field according to input string.
     */
    private void processInput() {
        try {
            handleInput(readLine());
        } catch (WrongFormatException ex) {
            System.err.println(ex.getMessage());
        }
    }

    private void handleInput(String input) throws WrongFormatException {
        boolean inNok = true;
        while (inNok) {
            System.out.println();
            System.out.println("Zadaj prikaz alebo \"H\" ak nepoznas prikazy");
            Pattern p = Pattern.compile("(?i)([X])?([H])?(([OM])([A-I])([0-8]))?");
            Matcher m = p.matcher(input.toLowerCase());
            if (m.matches()) {
                inNok = false;
                switch (m.group(0).charAt(0)) {
                    case 'x':
                        return;
                    case 'h':
                        System.out.println(
                                "X - ukonci" + "\n" +
                                        "MXY - oznac pole" + "\n" +
                                        "OXY - otvor pole" + "\n" +
                                        "X -> A-I, Y -> 0-8 (suradnice)");
                        processInput();
                        break;
                    case 'm':
                        int rowM = m.group(0).charAt(1) - 97;
                        int colM = m.group(0).charAt(2) - 48;
                        if (rowM < field.getRowCount() && colM < field.getColumnCount()) {
                            field.markTile(rowM, colM);
                        } else {
                            System.err.println("Suradnice mimo rozsah");
                        }
                        break;
                    case 'o':
                        int rowO = m.group(0).charAt(1) - 97;
                        int colO = m.group(0).charAt(2) - 48;
                        if (rowO < field.getRowCount() && colO < field.getColumnCount()) {
                            field.openTile(rowO, colO);
                        } else {
                            System.err.println("Suradnice mimo rozsah");
                        }
                        break;
                }
            } else {
                throw new WrongFormatException("Wrong Format Exception");
            }
        }
    }
}
