package com.bbuallbest.engine;

import com.bbuallbest.model.Cell;
import com.bbuallbest.model.PopulationBoard;

/**
 * Created by happy on 16/11/2014.
 */
public class GameOfLifeEngine {

    private static final int MIN_LIVE_NEIGHBOR_AMOUNT = 2;
    private static final int MAX_LIVE_NEIGHBOR_AMOUNT = 3;
    private static final int NEIGHBOR_AMOUNT_FOR_CELL_RESURRECTION = 3;


    public PopulationBoard generateNextPopulationState(PopulationBoard board) {
        System.out.println("INPUT: " + board.toString());
        PopulationBoard nextPopulationBoard = new PopulationBoard(board.getBoardLength());
        nextPopulationBoard.setBoard(nextPopulation(board.getBoard()));
        System.out.println("OUTPUT: " + nextPopulationBoard);
        return nextPopulationBoard;
    }

    private boolean[][] nextPopulation(boolean[][] previous) {
        boolean[][] next = new boolean[previous.length][previous.length];
        for (int i = 0; i < previous.length; i++) {
            for (int j = 0; j < previous.length; j++) {
               next[i][j] = willCellAlive(i, j, previous);
            }
        }
        return next;
    }

    private boolean willCellAlive(int row, int column, boolean[][] previousPopulation) {
        int aroundLiveCellsAmount = countAroundLiveCells(row, column, previousPopulation);
        if(previousPopulation[row][column]) {
            if(aroundLiveCellsAmount >= MIN_LIVE_NEIGHBOR_AMOUNT && aroundLiveCellsAmount <= MAX_LIVE_NEIGHBOR_AMOUNT)
                return true;
            return false;
        } else {
            if (aroundLiveCellsAmount == NEIGHBOR_AMOUNT_FOR_CELL_RESURRECTION)
                return true;
            return false;
        }
    }

    private int countAroundLiveCells(int row, int column, boolean[][] previousPopulation) {
        int aliveCounter = 0;
        if (isLeftAlive(row, column, previousPopulation))
            aliveCounter++;
        if (isLeftTopAlive(row, column, previousPopulation))
            aliveCounter++;
        if (isTopAlive(row, column, previousPopulation))
            aliveCounter++;
        if (isRightTopAlive(row, column, previousPopulation))
            aliveCounter++;
        if (isRightAlive(row, column, previousPopulation))
            aliveCounter++;
        if (isRightBottomAlive(row, column, previousPopulation))
            aliveCounter++;
        if (isBottomAlive(row, column, previousPopulation))
            aliveCounter++;
        if (isLeftBottomAlive(row, column, previousPopulation))
            aliveCounter++;
        return aliveCounter;
    }

    private boolean isLeftAlive(int row, int column, boolean[][] previousPopulation) {
        if(column == 0)
            column = previousPopulation.length;
        return previousPopulation[row][column - 1];
    }

    private boolean isLeftTopAlive(int row, int column, boolean[][] previousPopulation) {
        if(row == 0)
            row = previousPopulation.length;
        if(column == 0)
            column = previousPopulation.length;
        return previousPopulation[row - 1][column - 1];
    }

    private boolean isTopAlive(int row, int column, boolean[][] previousPopulation) {
        if(row == 0)
            row = previousPopulation.length;
        return previousPopulation[row - 1][column];
    }

    private boolean isRightTopAlive(int row, int column, boolean[][] previousPopulation) {
        if(row == 0)
            row = previousPopulation.length;
        if(column == previousPopulation.length - 1)
            column = -1;
        return previousPopulation[row - 1][column + 1];
    }

    private boolean isRightAlive(int row, int column, boolean[][] previousPopulation) {
        if(column == previousPopulation.length - 1)
            column = -1;
        return previousPopulation[row][column + 1];
    }

    private boolean isRightBottomAlive(int row, int column, boolean[][] previousPopulation) {
        if(row == previousPopulation.length - 1)
            row = -1;
        if(column == previousPopulation.length - 1)
            column = -1;
        return previousPopulation[row + 1][column + 1];
    }

    private boolean isBottomAlive(int row, int column, boolean[][] previousPopulation) {
        if(row == previousPopulation.length - 1)
            row = -1;
        return previousPopulation[row + 1][column];
    }

    private boolean isLeftBottomAlive(int row, int column, boolean[][] previousPopulation) {
        if(row == previousPopulation.length - 1)
            row = -1;
        if(column == 0)
            column = previousPopulation.length;
        return previousPopulation[row + 1][column - 1];
    }

    public PopulationBoard generateRandomPopulation(int boardSize, int population) {
        PopulationBoard board = new PopulationBoard(boardSize);
        while (board.getPopulationStateList().size() < population)
            board.addCell(generateRandomCell(boardSize));
        return board;
    }

    private Cell generateRandomCell(int boardSize) {
        Cell cell = new Cell();
        cell.setRow((int) (Math.random() * boardSize));
        cell.setColumn((int) (Math.random() * boardSize));
        return cell;
    }
}
