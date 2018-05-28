package sk.tuke.gamestudio.client;

import sk.tuke.gamestudio.entity.Rating;
import sk.tuke.gamestudio.service.RatingService;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

public class RatingRestServiceClient implements RatingService {
    private static final String URL = "http://localhost:8080/gamestudio_war_exploded/api/rating";

    @Override
    public void setRating(Rating rating) {
        try {
            Client client = ClientBuilder.newClient();
            Response response = client.target(URL)
                    .request(MediaType.APPLICATION_JSON)
                    .post(Entity.entity(rating, MediaType.APPLICATION_JSON), Response.class);
        } catch (Exception e) {
            throw new RuntimeException("Error adding rating", e);
        }
    }

    @Override
    public String getAverageRating(String game) {
        try {
            Client client = ClientBuilder.newClient();
            return client.target(URL)
                    .path("/" + game + "/average")
                    .request(MediaType.APPLICATION_JSON)
                    .get(new GenericType<String>() {});
        } catch (Exception e) {
            throw new RuntimeException("Error loading rating", e);
        }
    }

    public List<Rating> getAllRatings(String game) {
        try {
            Client client = ClientBuilder.newClient();
            return client.target(URL)
                    .path("/" + game)
                    .request(MediaType.APPLICATION_JSON)
                    .get(new GenericType<List<Rating>>() {});
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }
}
