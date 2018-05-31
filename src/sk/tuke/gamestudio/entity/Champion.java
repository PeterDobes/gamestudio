package sk.tuke.gamestudio.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Champion {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Integer ident;
    private String player;
    private String game;
    private Date date;
    private String previous;

    public Champion() {
    }

    public Champion(String player, String game, Date date, String previous) {
        this.player = player;
        this.game = game;
        this.date = date;
        this.previous = previous;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getPrevious() {
        return previous;
    }

    public void setPrevious(String previous) {
        this.previous = previous;
    }
}
