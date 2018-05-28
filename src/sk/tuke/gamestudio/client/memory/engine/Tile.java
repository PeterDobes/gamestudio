package sk.tuke.gamestudio.client.memory.engine;

public class Tile {
    public enum State {
        OPEN,
        CLOSED
    }

    private State state = State.CLOSED;

    public State getState() {
        return state;
    }

    void setState(State state) {
        this.state = state;
    }
}
