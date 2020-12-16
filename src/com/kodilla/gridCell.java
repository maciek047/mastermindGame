package com.kodilla;

public class gridCell {
    private int row;
    private int col;
    public gridCell(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int getRow() {
        return row;
    }
    public int getCol() {
        return col;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof gridCell)) return false;

        gridCell gridCell = (gridCell) o;

        if (getRow() != gridCell.getRow()) return false;
        return getCol() == gridCell.getCol();
    }

    @Override
    public int hashCode() {
        int result = getRow();
        result = 31 * result + getCol();
        return result;
    }
}