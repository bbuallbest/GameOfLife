package com.bbuallbest.entity;

import com.bbuallbest.model.Cell;

import java.util.List;

/**
 * Created by happy on 16/11/2014.
 */
public class PopulationState {
    private int worldSize;
    private List<Cell> cellList;

    public PopulationState() {}

    public PopulationState(int worldSize, List<Cell> cellList) {
        this.worldSize = worldSize;
        this.cellList = cellList;
    }

    public int getWorldSize() {
        return worldSize;
    }

    public void setWorldSize(int worldSize) {
        this.worldSize = worldSize;
    }

    public List<Cell> getCellList() {
        return cellList;
    }

    public void setCellList(List<Cell> cellList) {
        this.cellList = cellList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PopulationState that = (PopulationState) o;

        if (worldSize != that.worldSize) return false;
        if (!cellList.equals(that.cellList)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = worldSize;
        result = 31 * result + cellList.hashCode();
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("PopulationState{");
        sb.append("worldSize=").append(worldSize);
        sb.append(", cellList=").append(cellList);
        sb.append('}');
        return sb.toString();
    }
}
