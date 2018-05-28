package sk.tuke.gamestudio.entity;

import javax.persistence.*;

@Entity
@NamedQuery(name = "Comment.getAllComments",
        query = "SELECT c FROM Comment c WHERE c.game=:game")
public class Comment {
    @Id
    @GeneratedValue (strategy = GenerationType.TABLE)
    private Integer ident;
    private String player;
    private String game;
    private String text;

    public Comment() {
    }

    public Comment(String player, String game, String text) {
        this.player = player;
        this.game = game;
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String comment) {
        this.text = comment;
    }

    public Integer getIdent() {
        return ident;
    }

    public void setIdent(Integer ident) {
        this.ident = ident;
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

    @Override
    public String toString() {
        return "Comment{" +
                "player='" + player + '\'' +
                ", game='" + game + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}
