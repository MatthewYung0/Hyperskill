package battleship;

import java.util.ArrayList;
import java.util.List;

public class Player {

    private char[][] battlefield;
    private char[][] shipPositions;
    private List<List<String>> shipStatuses;

    Player(int maxRow, int maxColumn) {
        this.battlefield = new char[maxRow][maxColumn];
        this.shipPositions = new char[maxRow][maxColumn];
        this.shipStatuses = new ArrayList<List<String>>();
    }
    public void setBattlefield(int row, int column, char status) {
        this.battlefield[row][column] = status;
    }

    public void setShipPositions(int row, int column, char status) {
        this.shipPositions[row][column] = status;
    }

    public void setShipStatuses(List<List<String>> shipStatuses) {
        this.shipStatuses = shipStatuses;
    }

    public char[][] getBattlefield() {
        return this.battlefield;
    }

    public char[][] getShipPositions() {
        return this.shipPositions;
    }

    public List<List<String>> getShipStatuses() {
        return this.shipStatuses;
    }

}
