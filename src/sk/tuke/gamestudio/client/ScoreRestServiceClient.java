package sk.tuke.gamestudio.client;

import sk.tuke.gamestudio.entity.Score;
import sk.tuke.gamestudio.service.ScoreService;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class ScoreRestServiceClient implements ScoreService {
    private static final String URL = "http://localhost:8080/gamestudio_war_exploded/api/score";

    @Override
    public void addScore(Score score)  {
        try {
            Client client = ClientBuilder.newClient();
            Response response = client.target(URL)
                    .request(MediaType.APPLICATION_JSON)
                    .post(Entity.entity(score, MediaType.APPLICATION_JSON), Response.class);
        } catch (Exception e) {
            throw new RuntimeException("Error saving score", e);
        }
    }

    @Override
    public List<Score> getBestScoresForGame(String game) {
        try {
            Client client = ClientBuilder.newClient();
            return client.target(URL)
                    .path("/" + game)
                    .request(MediaType.APPLICATION_JSON)
                    .get(new GenericType<List<Score>>() {
                    });
        } catch (Exception e) {
            System.err.println("Error loading score");
            return new ArrayList<>();
        }
    }

    @Override
    public List<Score> getChampion(String game) {
        try {
            Client client = ClientBuilder.newClient();
            return client.target(URL)
                    .path("/" + game + "/champion")
                    .request(MediaType.APPLICATION_JSON)
                    .get(new GenericType<List<Score>>() {
                    });
        } catch (Exception e) {
            System.err.println("Error loading the champion");
            return new ArrayList<>();
        }
    }
}
