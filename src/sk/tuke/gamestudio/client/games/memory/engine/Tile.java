package sk.tuke.gamestudio.client.games.memory.engine;

public class Tile {
    public enum State {
        OPEN,
        CLOSED,
        HIGHLIGHT
    }

    private State state = State.CLOSED;

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }
}
