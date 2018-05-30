package sk.tuke.gamestudio.webservice;

import sk.tuke.gamestudio.entity.Comment;
import sk.tuke.gamestudio.service.interfaces.CommentService;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/comment")
public class CommentRestService {
    @EJB
    private CommentService commentService;

    @POST
    @Consumes("application/json")
    public Response addComment(Comment comment) {
        commentService.addComment(comment);
        return Response.ok().build();
    }

    @GET
    @Path("/{game}")
    @Produces("application/json")
    public List<Comment> getAllComments(@PathParam("game") String game){
        return commentService.getAllComments(game);
    }
}

