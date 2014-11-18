package com.bbuallbest.resource;

import com.bbuallbest.sevice.GameOfLifePopulation;
import com.bbuallbest.sevice.PopulationService;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;

import javax.inject.Singleton;
import javax.ws.rs.ApplicationPath;

/**
 * Created by happy on 17/11/2014.
 */
@ApplicationPath("/api/rest")
public class JerseyConfig extends ResourceConfig {
    public JerseyConfig() {
        register(new AbstractBinder() {
            @Override
            protected void configure() {
                bind(GameOfLifePopulation.class)
                        .to(PopulationService.class)
                        .in(Singleton.class);
            }
        });
        register(PopulationResource.class);
    }
}
