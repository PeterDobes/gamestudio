package sk.tuke.gamestudio.entity;

import javax.persistence.*;

@Entity
@NamedQueries({
        @NamedQuery(name = "Rating.getAverageRating",
                query = "SELECT avg(r.stars) FROM Rating r WHERE r.game=:game"),
        @NamedQuery(name = "Rating.checkForRating",
                query = "SELECT r FROM Rating r WHERE r.game=:game and r.player=:player"),
        @NamedQuery(name = "Rating.getAllRatings",
                query = "SELECT r FROM Rating r WHERE r.game=:game ORDER BY r.stars")})
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Integer id;
    private String player;
    private String game;
    private Double stars;

    public Rating() {
    }

    public Rating(String player, String game, Double stars) {
        this.player = player;
        this.game = game;
        this.stars = stars;
    }

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public String getGame() {
        return game;
    }

    public void setGame(String game) {
        this.game = game;
    }

    public Double getStars() {
        return stars;
    }

    public void setStars(Double stars) {
        this.stars = stars;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Rating{" +
                "player='" + player + '\'' +
                ", game='" + game + '\'' +
                ", stars=" + stars +
                '}';
    }
}
