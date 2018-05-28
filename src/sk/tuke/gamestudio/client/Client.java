package sk.tuke.gamestudio.client;

public class Client {

    private ClientConsole clientConsole;

    public static void main(String[] args) {
        new Client();
    }

    private Client() {
        clientConsole = new ClientConsole();
        clientConsole.choosingGame();
    }
}
