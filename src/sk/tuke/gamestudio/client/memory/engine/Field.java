package sk.tuke.gamestudio.client.memory.engine;

import java.util.Random;

public class Field {

    private Tile[][] tiles;

    private int rowCount;

    private int columnCount;

    private int targetCount;

    private int points;

    private int errors;

    public Field(int rowCount, int columnCount, int targetCount, int points) {
        this.rowCount = rowCount;
        this.columnCount = columnCount;
        this.targetCount = targetCount;
        tiles = new Tile[rowCount][columnCount];
        this.points = points;
        errors = 0;

        generate();
    }

    public void openTile(int row, int column) {
        Tile tile = tiles[row][column];
        if (tile.getState() == Tile.State.CLOSED) {
            tile.setState(Tile.State.OPEN);
            if (tile instanceof Target) {
                points += targetCount * 10;
            } else {
                errors += 1;
            }
        }
    }

    public void generate() {
        Random rows = new Random();
        Random cols = new Random();
        int targets = targetCount;

        while (targets > 0) {
            int row = rows.nextInt(rowCount);
            int col = cols.nextInt(columnCount);
            if (tiles[row][col] == null) {
                tiles[row][col] = new Target();
                targets--;
            }
        }

        for(int i = 0; i < rowCount; i++){
            for(int j = 0; j < columnCount; j++){
                if (tiles[i][j] == null) {
                    tiles[i][j] = new Tile();
                }
            }
        }
    }

    public boolean isSolved() {
        return (getNumberOf(Tile.State.OPEN) >= (targetCount));
    }

    private int getNumberOf(Tile.State state) {
        int count = 0;
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < columnCount; j++) {
                if (tiles[i][j].getState() == state) {
                    count++;
                }
            }
        }
        return count;
    }

    public Tile[][] getTiles() {
        return tiles;
    }

    public Tile getTile(int row, int column) {
        return tiles[row][column];
    }

    public int getRowCount() {
        return rowCount;
    }

    public int getColumnCount() {
        return columnCount;
    }

    public int getTargetCount() {
        return targetCount;
    }

    public int getPoints() {
        return points;
    }

    public int getErrors() {
        return errors;
    }

    public void setRowCount(int rowCount) {
        this.rowCount = rowCount;
    }

    public void setColumnCount(int columnCount) {
        this.columnCount = columnCount;
    }

    public void setTargetCount(int targetCount) {
        this.targetCount = targetCount;
    }
}
