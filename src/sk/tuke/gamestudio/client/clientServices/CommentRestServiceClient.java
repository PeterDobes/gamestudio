package sk.tuke.gamestudio.client.clientServices;

import sk.tuke.gamestudio.entity.Comment;
import sk.tuke.gamestudio.service.interfaces.CommentService;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

public class CommentRestServiceClient implements CommentService {
    private static final String URL = "http://localhost:8080/gamestudio_war_exploded/api/comment";

    @Override
    public void addComment(Comment comment) {
        try {
            Client client = ClientBuilder.newClient();
            Response response = client.target(URL)
                    .request(MediaType.APPLICATION_JSON)
                    .post(Entity.entity(comment, MediaType.APPLICATION_JSON), Response.class);
        } catch (Exception e) {
            System.err.println("Error adding comment");
        }
    }

    public List<Comment> getAllComments(String game) {
        try {
            Client client = ClientBuilder.newClient();
            return client.target(URL)
                    .path("/" + game)
                    .request(MediaType.APPLICATION_JSON)
                    .get(new GenericType<List<Comment>>() {});
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }
}
