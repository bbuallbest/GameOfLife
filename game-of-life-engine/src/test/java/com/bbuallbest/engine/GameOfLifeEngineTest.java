package com.bbuallbest.engine;

import com.bbuallbest.model.Cell;
import com.bbuallbest.model.PopulationBoard;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

/**
 * Created by happy on 16/11/2014.
 */

public class GameOfLifeEngineTest {

    private static final boolean T = true;
    private static final boolean F = false;

    private GameOfLifeEngine engine= new GameOfLifeEngine();


    @Test
    public void testGenerateNextPopulation() {
        boolean[][] actual;
        boolean[][] test = {{F, F, T, F},
                            {F, T, F, T},
                            {T, F, T, F},
                            {F, F, F, F}};
        boolean[][] expected = {{F, F, T, F},
                                {T, T, F, T},
                                {T, T, T, T},
                                {F, T, F, T}};

        PopulationBoard actualBoard;
        PopulationBoard testBoard = new PopulationBoard(test.length);
        testBoard.setBoard(test);
        actualBoard = engine.generateNextPopulationState(testBoard);

        actual = actualBoard.getBoard();

        for (int i = 0; i < test.length; i++) {
            Assert.assertTrue("row=" + i, Arrays.equals(expected[i], actual[i]));
        }
    }
}
