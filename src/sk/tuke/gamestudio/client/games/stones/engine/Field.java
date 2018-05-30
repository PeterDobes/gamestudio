package sk.tuke.gamestudio.client.games.stones.engine;

import java.io.Serializable;

public class Field implements Serializable {
    private Stone[][] stones;
    private final int rowCount;
    private final int columnCount;
    private final int stoneCount;
    private Space space;
    private long time;

    public Field(int rowCount, int columnCount) {
        this.rowCount = rowCount;
        this.columnCount = columnCount;
        this.stoneCount = rowCount * columnCount;
        this.time = 0L;

        scramble();
    }

    public void scramble() {
        int ston = 1;
        stones = new Stone[rowCount][columnCount];
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < columnCount; j++) {
                if (ston == stoneCount) {
                    space = new Space(i, j);
                }
                stones[i][j] = new Stone(ston);
                ston++;
            }
        }
    }

    public boolean isSolved() {
        int expected = 1;
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j <= (columnCount -1); j++) {
                if (getStone(i, j).getValue() != expected) {
                    return false;
                }
                expected++;
            }
        }
        return true;
    }

    public Stone getStone(int row, int col) {
        return stones[row][col];
    }

    public int getStoneCount() {
        return stoneCount;
    }

    public int getRowCount() {
        return rowCount;
    }

    public int getColumnCount() {
        return columnCount;
    }

    public Space getSpace() {
        return space;
    }

    public void setSpace(Space space) {
        this.space = space;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
