package com.bbuallbest.sevice;

import com.bbuallbest.entity.PopulationState;

/**
 * Created by happy on 17/11/2014.
 */
public interface PopulationService {
    PopulationState generateRandomPopulation(int worldSize, int populationAmount);
    PopulationState generateNextPopulation(PopulationState previousState);
}
