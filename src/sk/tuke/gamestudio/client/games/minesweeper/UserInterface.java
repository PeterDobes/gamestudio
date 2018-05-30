package sk.tuke.gamestudio.client.games.minesweeper;

import sk.tuke.gamestudio.client.games.minesweeper.core.Field;

public interface UserInterface {
    void newGameStarted(Field field);

    void update();
}
