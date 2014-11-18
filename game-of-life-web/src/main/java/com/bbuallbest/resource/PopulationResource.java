package com.bbuallbest.resource;

import com.bbuallbest.entity.PopulationState;
import com.bbuallbest.journal.Importance;
import com.bbuallbest.journal.Record;
import com.bbuallbest.sevice.PopulationService;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.text.DateFormat;

/**
 * Created by happy on 16/11/2014.
 */

@Path("/population")
public class PopulationResource {

    private static final String INPUT_LOG_PREFIX = " input population amount: ";
    private static final String OUTPUT_LOG_PREFIX = "output population amount: ";

    @Context private HttpServletRequest httpRequest;

    @Inject
    private PopulationService populationService;

    @POST
    @Path("/next")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getNextPopulation(PopulationState state) {
        printLog(INPUT_LOG_PREFIX + state.getCellList().size());
        PopulationState nextPopulationState = populationService.generateNextPopulation(state);
        printLog(OUTPUT_LOG_PREFIX + state.getCellList().size());
        return Response
                .ok()
                .entity(nextPopulationState)
                .build();
    }

    @GET
    @Path("/{population}/random/{world}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRandomPopulation(@PathParam("population") int populationAmount,
                                        @PathParam("world") int worldSize) {
        return Response
                .ok()
                .entity(populationService.generateRandomPopulation(worldSize, populationAmount))
                .build();
    }

    private void printLog(String message) {
        String source = httpRequest.getRemoteHost() + ":" + httpRequest.getRemotePort();
        Record record = new Record(new DateTime(), Importance.NORMAL, source, message);
        System.out.println(record);
    }
}
