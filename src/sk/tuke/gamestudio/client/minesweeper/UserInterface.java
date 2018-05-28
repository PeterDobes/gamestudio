package sk.tuke.gamestudio.client.minesweeper;

import sk.tuke.gamestudio.client.minesweeper.core.Field;

public interface UserInterface {
    void newGameStarted(Field field);

    void update();
}
