package sk.tuke.gamestudio.webservice;

import sk.tuke.gamestudio.entity.Rating;
import sk.tuke.gamestudio.service.RatingService;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/rating")
public class RatingRestService {
    @EJB
    private RatingService ratingService;

    @POST
    @Consumes("application/json")
    public Response setRating(Rating rating) {
        ratingService.setRating(rating);
        return Response.ok().build();
    }

    @GET
    @Path("/{game}/average")
    @Produces("application/json")
    public String getAverageRating(@PathParam("game") String game) {
        return ratingService.getAverageRating(game);
    }

    @GET
    @Path("/{game}")
    @Produces("application/json")
    public List<Rating> getAllRatings(@PathParam("game") String game) {
        return ratingService.getAllRatings(game);
    }

}
