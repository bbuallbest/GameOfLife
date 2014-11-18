package com.bbuallbest.resource;

import com.bbuallbest.entity.PopulationState;
import com.bbuallbest.sevice.PopulationService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by happy on 16/11/2014.
 */

@Path("/population")
public class PopulationResource {

    @Inject
    private PopulationService populationService;

    @POST
    @Path("/next")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getNextPopulation(PopulationState state) {
        return Response
                .ok()
                .entity(populationService.generateNextPopulation(state))
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
}
