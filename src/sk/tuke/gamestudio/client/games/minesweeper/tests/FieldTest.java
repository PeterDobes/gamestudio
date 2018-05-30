package sk.tuke.gamestudio.client.games.minesweeper.tests;

import static org.junit.Assert.*;

import sk.tuke.gamestudio.client.games.minesweeper.core.Clue;
import sk.tuke.gamestudio.client.games.minesweeper.core.Field;
import sk.tuke.gamestudio.client.games.minesweeper.core.GameState;
import sk.tuke.gamestudio.client.games.minesweeper.core.Mine;
import org.junit.Assert;
import org.junit.Test;

public class FieldTest {
    static final int ROWS = 9;
    static final int COLUMNS = 9;
    static final int MINES = 10;

    @Test
    public void manyGenerate() {
        for (int i = 0; i < 1000000; i++) {
            generate();
        }
    }

    @Test
    public void generate() {
        Field field = new Field(ROWS, COLUMNS, MINES);
        int mineCount = 0;
        int clueCount = 0;
        assertEquals(ROWS, field.getRowCount());
        assertEquals(COLUMNS, field.getColumnCount());
        assertEquals(MINES, field.getMineCount());
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                assertNotNull(field.getTile(i,j));
                if (field.getTile(i,j) instanceof Mine) {
                    mineCount++;
                } else if (field.getTile(i,j) instanceof Clue) {
                    clueCount++;
                }

            }
        }
        assertEquals(MINES, mineCount);
        assertEquals(ROWS * COLUMNS - MINES, clueCount);

    }

    @Test
    public void isSolved() {
        Field field = new Field(ROWS, COLUMNS, MINES);

        Assert.assertEquals(GameState.PLAYING, field.getState());

        int open = 0;
        for(int row = 0; row < field.getRowCount(); row++) {
            for(int column = 0; column < field.getColumnCount(); column++) {
                if(field.getTile(row, column) instanceof Clue) {
                    field.openTile(row, column);
                    open++;
                }
                if(field.getRowCount() * field.getColumnCount() - open == field.getMineCount()) {
                    assertEquals(GameState.SOLVED, field.getState());
                } else {
                    assertNotSame(GameState.FAILED, field.getState());
                }
            }
        }

        assertEquals(GameState.SOLVED, field.getState());
    }
}