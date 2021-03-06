 package sk.tuke.gamestudio.webservice;

import sk.tuke.gamestudio.entity.Score;
import sk.tuke.gamestudio.service.interfaces.ScoreException;
import sk.tuke.gamestudio.service.interfaces.ScoreService;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/score")
public class ScoreRestService {
    @EJB
    private ScoreService scoreService;

    @POST
    @Consumes("application/json")
    public Response addScore(Score score) throws ScoreException {
        scoreService.addScore(score);
        return Response.ok().build();
    }

    @GET
    @Path("/{game}")
    @Produces("application/json")
    public List<Score> getBestScoresForGame(@PathParam("game") String game) throws ScoreException {
        return scoreService.getBestScoresForGame(game);
    }

    @GET
    @Path("/{game}/champion")
    @Produces("application/json")
    public Score getChampion(@PathParam("game") String game) {
        return scoreService.getChampion(game);
    }
}

