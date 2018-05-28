package sk.tuke.gamestudio.client.minesweeper.core;

import java.util.Random;

/**
 * Field represents playing field and game logic.
 */
public class Field {
    /**
     * Playing field tiles.
     */
    private final Tile[][] tiles;

    private final int rowCount;

    private final int columnCount;

    private final int mineCount;

    private GameState state = GameState.PLAYING;

    /**
     * Constructor.
     *
     * @param rowCount    row count
     * @param columnCount column count
     * @param mineCount   mine count
     */
    public Field(int rowCount, int columnCount, int mineCount) {
        this.rowCount = rowCount;
        this.columnCount = columnCount;
        this.mineCount = mineCount;
        tiles = new Tile[rowCount][columnCount];

        //generate the field content
        generate();
    }

    /**
     * Opens tile at specified indeces.
     *
     * @param row    row number
     * @param column column number
     */
    public void openTile(int row, int column) {
        Tile tile = tiles[row][column];
        if (tile.getState() == Tile.State.CLOSED) {
            tile.setState(Tile.State.OPEN);
            if (tile instanceof Mine) {
                state = GameState.FAILED;
                return;
            } else if (tile instanceof Clue) {
                if (((Clue) tile).getValue() == 0) {
                    openAdjacentTiles(row, column);
                }
            }

            if (isSolved()) {
                state = GameState.SOLVED;
                return;
            }
        }
    }

    /**
     * Marks tile at specified indeces.
     *
     * @param row    row number
     * @param column column number
     */
    public void markTile(int row, int column) {
        Tile.State state = tiles[row][column].getState();
        if (state == Tile.State.CLOSED) {
            if (getRemainingMineCount() == 0) {
                System.out.println("Can't mark any more tiles");
            } else {
                tiles[row][column].setState(Tile.State.MARKED);
            }
        } else if (state == Tile.State.MARKED) {
            tiles[row][column].setState(Tile.State.CLOSED);
        }
    }

    /**
     * Generates playing field.
     */
    private void generate() {
        Random rows = new Random();
        Random cols = new Random();
        int mines = mineCount;

        while(mines > 0){
            int row = rows.nextInt(rowCount);
            int col = cols.nextInt(columnCount);
            if(tiles[row][col] == null){
                tiles[row][col] = new Mine();
                mines--;
            }
        }

        for(int i = 0; i < rowCount; i++){
            for(int j = 0; j < columnCount; j++){
                if(tiles[i][j] == null){
                    tiles[i][j] = new Clue(countAdjacentMines(i,j));

                }
            }
        }
    }


    /**
     * Returns true if game is solved, false otherwise.
     *
     * @return true if game is solved, false otherwise
     */
    private boolean isSolved() {
        return ((rowCount * columnCount) - getNumberOf(Tile.State.OPEN)) == mineCount;
    }

    private int getNumberOf(Tile.State state){
        int count = 0;
        for(int i = 0; i < rowCount; i++){
            for(int j = 0; j < columnCount; j++){
                if(tiles[i][j].getState() == state){
                    count++;
                }
            }
        }
        return count;
    }

    /**
     * Returns number of adjacent mines for a tile at specified position in the field.
     *
     * @param row    row number.
     * @param column column number.
     * @return number of adjacent mines.
     */
    private int countAdjacentMines(int row, int column) {
        int count = 0;
        for (int rowOffset = -1; rowOffset <= 1; rowOffset++) {
            int actRow = row + rowOffset;
            if (actRow >= 0 && actRow < getRowCount()) {
                for (int columnOffset = -1; columnOffset <= 1; columnOffset++) {
                    int actColumn = column + columnOffset;
                    if (actColumn >= 0 && actColumn < getColumnCount()) {
                        if (tiles[actRow][actColumn] instanceof Mine) {
                            count++;
                        }
                    }
                }
            }
        }

        return count;
    }

    private void openAdjacentTiles(int row, int column) {
        for (int rowOffset = -1; rowOffset <= 1; rowOffset++) {
            int actRow = row + rowOffset;
            if (actRow >= 0 && actRow < getRowCount()) {
                for (int columnOffset = -1; columnOffset <= 1; columnOffset++) {
                    int actColumn = column + columnOffset;
                    if (actColumn >= 0 && actColumn < getColumnCount()) {
                        if (tiles[actRow][actColumn] instanceof Clue &&
                                tiles[actRow][actColumn].getState() == Tile.State.CLOSED) {
                            openTile(actRow,actColumn);
                        }
                    }
                }
            }
        }
    }

    /**
     * Field row count. Rows are indexed from 0 to (rowCount - 1).
     */
    public int getRowCount() {
        return rowCount;
    }

    /**
     * Column count. Columns are indexed from 0 to (columnCount - 1).
     */
    public int getColumnCount() {
        return columnCount;
    }

    /**
     * Mine count.
     */
    public int getMineCount() {
        return mineCount;
    }

    /**
     * Game state.
     */
    public GameState getState() {
        return state;
    }

    public Tile getTile(int row, int column){
        return tiles[row][column];
    }

    public int getRemainingMineCount() {
        int marked = getNumberOf(Tile.State.MARKED);
        return mineCount - marked;
    }
}
