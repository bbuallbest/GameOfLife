package com.bbuallbest.sevice;

import com.bbuallbest.engine.GameOfLifeEngine;
import com.bbuallbest.entity.PopulationState;
import com.bbuallbest.model.PopulationBoard;

/**
 * Created by happy on 17/11/2014.
 */
public class GameOfLifePopulation implements PopulationService {

    private GameOfLifeEngine engine = new GameOfLifeEngine();

    @Override
    public PopulationState generateRandomPopulation(int worldSize, int populationAmount) {
        PopulationState state = new PopulationState();
        state.setWorldSize(worldSize);
        state.setCellList(engine
                .generateRandomPopulation(worldSize, populationAmount)
                .getPopulationStateList());
        return state;
    }

    @Override
    public PopulationState generateNextPopulation(PopulationState previousState) {
        PopulationState nextState = new PopulationState();
        nextState.setWorldSize(previousState.getWorldSize());
        nextState.setCellList(engine
                .generateNextPopulationState(
                        new PopulationBoard(previousState.getWorldSize(), previousState.getCellList()))
                .getPopulationStateList());
        return nextState;
    }
}
