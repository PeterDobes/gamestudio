package sk.tuke.gamestudio.client.games;

public interface GameUserInterface<T> {
    void newGame(T field);
    void update();
}
