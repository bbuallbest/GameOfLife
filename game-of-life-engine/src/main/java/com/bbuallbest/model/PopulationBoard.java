package com.bbuallbest.model;

import com.bbuallbest.exception.GameOfLifeException;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class PopulationBoard {
    private boolean[][] board;
    private List<Cell> populationStateList;

    public PopulationBoard(int boardSize) {
        board = new boolean[boardSize][boardSize];
        populationStateList = new LinkedList<Cell>();
    }

    public PopulationBoard(int boardSize, List<Cell> populationStateList) {
        board = new boolean[boardSize][boardSize];
        initBoard(populationStateList);
        this.populationStateList = populationStateList;
    }

    private void initBoard(List<Cell> state) {
        for(Cell cell : state) {
            validateCellPosition(cell);
            board[cell.getRow()][cell.getColumn()] = true;
        }
    }

    public boolean addCell(Cell cell) {
        validateCellPosition(cell);
        if (populationStateList.add(cell))
            board[cell.getRow()][cell.getColumn()] = true;
        else
            return false;
        return true;
    }

    private void validateCellPosition(Cell cell) {
        int x = cell.getRow();
        int y = cell.getColumn();
        if (x >= 0 || x < board.length || y >= 0 || y < board.length)
            return;
        else
            throw new GameOfLifeException("Illegal Cell state: x=" + x + " y=" + y
                    + ".Cell out of range.");
    }

    public int getBoardLength() {
        return board.length;
    }

    public void setBoard(boolean[][] board) {
        this.board = board;
        populationStateList = generatePopulationStateList();
    }

    private List<Cell> generatePopulationStateList() {
        List<Cell> cellList = new LinkedList<Cell>();
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (board[i][j])
                    cellList.add(new Cell(i, j));
            }
        }
        return cellList;
    }

    public boolean[][] getBoard() {
        return board;
    }

    public List<Cell> getPopulationStateList() {
        return populationStateList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PopulationBoard that = (PopulationBoard) o;

        if (populationStateList != null ? !populationStateList.equals(that.populationStateList) : that.populationStateList != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        return populationStateList != null ? populationStateList.hashCode() : 0;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("PopulationBoard{");
        sb.append("board=").append(Arrays.toString(board));
        sb.append(", populationStateList=").append(populationStateList);
        sb.append('}');
        return sb.toString();
    }
}
