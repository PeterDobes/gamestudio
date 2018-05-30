package sk.tuke.gamestudio.client;

import sk.tuke.gamestudio.client.clientServices.CommentRestServiceClient;
import sk.tuke.gamestudio.client.clientServices.RatingRestServiceClient;
import sk.tuke.gamestudio.client.clientServices.ScoreRestServiceClient;
import sk.tuke.gamestudio.client.clientServices.WeatherRestServiceClient;
import sk.tuke.gamestudio.client.games.memory.Memory;
import sk.tuke.gamestudio.client.games.minesweeper.Minesweeper;
import sk.tuke.gamestudio.client.games.stones.StonesPuzzle;
import sk.tuke.gamestudio.entity.Comment;
import sk.tuke.gamestudio.entity.Rating;
import sk.tuke.gamestudio.entity.Score;
import sk.tuke.gamestudio.entity.Weather.Clouds;
import sk.tuke.gamestudio.entity.Weather.WeatherMap;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ClientConsole implements UserInterface {

    private RatingRestServiceClient ratingService = new RatingRestServiceClient();
    private ScoreRestServiceClient scoreService = new ScoreRestServiceClient();
    private CommentRestServiceClient commentService = new CommentRestServiceClient();
    private WeatherRestServiceClient weatherService = new WeatherRestServiceClient();

    private String inGameName;
    private String lastPlayedGame;

    public enum Choice {
        GAME,
        RATING,
        COMMENT
    }

    private BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

    @Override
    public void choosingGame() {
        System.out.println("Welcome sir or madam " + System.getProperty("user.name"));
        WeatherMap weatherMap = weatherService.getForecast();
        System.out.println("Current temperature at Košice is :" + weatherMap.getMain().temp + " °C");
        Clouds clouds = weatherMap.getClouds();
        System.out.println("Cloudiness: " + clouds.all + "%");
        System.out.println("\nPlease enter your in-game name:");
        inGameName = readLine();
        if (inGameName.equals("")) {
            inGameName = "Anon";
        }
        while (true) {
            System.out.print("\nPlease choose a game\n1 - Minesweeeper - rating: ");
            System.out.println(ratingService.getAverageRating("minesweeper"));
            System.out.print("2 - Stones - rating: ");
            System.out.println(ratingService.getAverageRating("stones"));
            System.out.print("3 - Memory Matrix - rating: ");
            System.out.println(ratingService.getAverageRating("memory"));
            System.out.println("\nX - exit");
            processInput(Choice.GAME);
        }
    }

    private void gameSubMenu() {
        switch (lastPlayedGame) {
            case "minesweeper":
                System.out.println(
                        "" +
                                "　 　 　 　 　 ▒█▀▄▀█▒▀█▀ ▒█▄░▒█ ▒█▀▀▀ \n" +
                                "　 　 　 　 　 ▒█▒█▒█ ▒█░ ▒█▒█▒█ ▒█▀▀▀ \n" +
                                "　 　 　 　 　 ▒█░░▒█ ▄█▄ ▒█░░▀█ ▒█▄▄▄ \n" +
                                "\n" +
                                "▒█▀▀▀█ ▒█░░▒█ ▒█▀▀▀ ▒█▀▀▀ ▒█▀▀█ ▒█▀▀▀ ▒█▀▀█ \n" +
                                "░▀▀▀▄▄ ▒█▒█▒█ ▒█▀▀▀ ▒█▀▀▀ ▒█▄▄█ ▒█▀▀▀ ▒█▄▄▀ \n" +
                                "▒█▄▄▄█ ▒█▄▀▄█ ▒█▄▄▄ ▒█▄▄▄ ▒█░░░ ▒█▄▄▄ ▒█░▒█ \n\n\n");
                break;
            case "stones":
                System.out.println(
                        "" +
                                "▒█▀▀▀█  ▀▀█▀▀ ▒█▀▀▀█ ▒█▄░▒█ ▒█▀▀▀ ▒█▀▀▀█ \n" +
                                "░▀▀▀▄▄ ░▒█░░ ▒█░░▒█ ▒█▒█▒█ ▒█▀▀▀ ░▀▀▀▄▄ \n" +
                                "▒█▄▄▄█ ░▒█░░ ▒█▄▄▄█ ▒█░░▀█ ▒█▄▄▄ ▒█▄▄▄█ \n\n\n");
                break;
            case "memory":
                System.out.println(
                        "" +
                                "▒█▀▄▀█ ▒█▀▀▀ ▒█▀▄▀█  ▒█▀▀▀█ ▒█▀▀█ ▒█░░▒█ \n" +
                                "▒█▒█▒█ ▒█▀▀▀ ▒█▒█▒█ ▒█░░▒█ ▒█▄▄▀ ▒█▄▄▄█ \n" +
                                "▒█░░▒█ ▒█▄▄▄ ▒█░░▒█ ▒█▄▄▄█ ▒█░▒█ ░░▒█░░ \n" +
                                "\n" +
                                "▒█▀▄▀█ ░█▀▀█  ▀▀█▀▀▒▒█▀▀█ ▀█▀ ▀▄▒▄▀ \n" +
                                "▒█▒█▒█ ▒█▄▄█   ▒█░░ ▒█▄▄▀   █░ ░▒█░░ \n" +
                                "▒█░░▒█ ▒█░▒█ ░▒█░░ ▒█░▒█ ▄█▄ ▄▀▒▀▄ \n\n\n");
                break;
        }

        System.out.println("Rating: " + ratingService.getAverageRating(lastPlayedGame));
        getChampion(lastPlayedGame);
        while (true) {
            System.out.println("\n1 - Run");
            System.out.println("2 - Rate the game");
            System.out.println("3 - Leave a comment");
            System.out.println("4 - View leaderboard");
            System.out.println("5 - View all ratings");
            System.out.println("6 - View comments");
            System.out.println("\nX - Return to menu");
            System.out.println();
            String input = readLine();
            Pattern p = Pattern.compile("(?i)([X])?([123456])?");
            Matcher m = p.matcher(input.toLowerCase());
            if (m.matches() && !input.equals("")) {
                switch (m.group(0).charAt(0)) {
                    case 'x':
                        return;
                    case '1':
                        launch();
                        processInput(Choice.RATING);
                        break;
                    case '2':
                        processInput(Choice.RATING);
                        break;
                    case '3':
                        leaveAComment();
                        break;
                    case '4':
                        viewLeaderboard();
                        break;
                    case '5':
                        viewAllRatings();
                        break;
                    case '6':
                        viewAllComments();
                }
            } else {
                System.err.println("Not an option");
            }
        }
    }

    private String readLine() {
        try {
            return input.readLine();
        } catch (IOException e) {
            return null;
        }
    }

    private void processInput(Choice choice) {
        switch (choice) {
            case GAME:
                pickGame();
                break;
            case RATING:
                rateGame();
                break;
            case COMMENT:
                break;
        }

    }

    private void rateGame() {
        String input;
        int rate = -1;
        do {
            System.out.println("\nWould you like to rate the game?");
            System.out.println("Rating 1 - 5 or 0 for no rating");
            input = readLine();
            if (Pattern.matches("^\\d+$", input)) {
                rate = Integer.parseInt(input);
            } else {
                System.out.println("Not an option");
            }
        } while (rate < 0 || rate > 5);

        if (rate != 0) {
            Rating rating = new Rating(inGameName, lastPlayedGame, (double) rate);
            ratingService.setRating(rating);
        }
    }

    private void pickGame() {
        boolean inNok = true;
        while (inNok) {
            String input = readLine();
            System.out.println();
            Pattern p = Pattern.compile("(?i)([X])?([123])?");
            Matcher m = p.matcher(input.toLowerCase());
            if (m.matches() && !input.equals("")) {
                inNok = false;
                switch (m.group(0).charAt(0)) {
                    case 'x':
                        System.exit(0);
                        break;
                    case '1':
                        lastPlayedGame = "minesweeper";
                        break;
                    case '2':
                        lastPlayedGame = "stones";
                        break;
                    case '3':
                        lastPlayedGame = "memory";
                        break;
                }
            } else {
                System.err.println("Not an option");
            }
        }
        gameSubMenu();
    }

    private void leaveAComment() {
        System.out.println("Comment:");
        String text = readLine();
        if (!text.equals("")) {
            commentService.addComment(new Comment(inGameName, lastPlayedGame, text));
        }
    }

    private void launch() {
        switch (lastPlayedGame) {
            case "minesweeper":
                new Minesweeper(inGameName);
                break;
            case "stones":
                new StonesPuzzle(inGameName);
                break;
            case "memory":
                new Memory(inGameName);
                break;
        }
    }

    private void viewAllRatings() {
        System.out.println("Single ratings:");
        List<Rating> ratings = ratingService.getAllRatings(lastPlayedGame);
        if (ratings.isEmpty()) {
            System.out.println("-no ratings yet-");
        } else {
            for (Rating rating : ratings) {
                System.out.println(rating.getPlayer() + " - " + rating.getStars());
            }
        }
    }

    private void viewLeaderboard() {
        System.out.println("Leaderboard:");
        List<Score> scores = scoreService.getBestScoresForGame(lastPlayedGame);
        if (scores.isEmpty()) {
            System.out.println("-no high scores yet-");
        } else {
            for (Score score : scores) {
                System.out.println(score.getPlayer() + " - " + score.getPoints() + " points");
            }
        }
    }

    private void viewAllComments() {
        System.out.println("Comment section:");
        List<Comment> comments = commentService.getAllComments(lastPlayedGame);
        if (comments.isEmpty()) {
            System.out.println("-no comments yet-");
        } else {
            for (Comment comment : comments) {
                System.out.println(comment.getPlayer() + ": " + comment.getText());
            }
        }
    }

    private void getChampion(String game) {
        List<Score> champion = scoreService.getChampion(game);
        if (!champion.isEmpty()) {
            System.out.println("Champion of the game: " + champion.get(0).getPlayer() + " - " +
                    champion.get(0).getPoints());
        } else {
            System.out.println("-no champions yet-");
        }
    }
}