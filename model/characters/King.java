package model.characters;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * King class
 */
public class King implements Character {

    private int i;
    //x coordinate

    private int j;
    //y coordinate

    /**
     * Constructor
     *
     * @param i - x coordinate
     * @param j - y coordinate
     */
    public King(int i, int j) {
        this.i = i;
        this.j = j;
    }

    /**
     * Generate all possible spot that bishop can go to with given board
     *
     * @param board - model board
     * @return A list of Coordinate
     */
    @Override
    public ArrayList<ArrayList<Integer>> possibleMove(String[][] board) {
        ArrayList<ArrayList<Integer>> possibleList = new ArrayList<>();
        ArrayList<Integer> removeIndexes = new ArrayList<>();
        for (int x = this.i - 1; x <= this.i + 1; x++) {
            for (int y = this.j - 1; y <= this.j + 1; y++) {
                if (x >= 0 && x <= 3 && y >= 0 && y <= 3) {
                    ArrayList<Integer> spot = new ArrayList<>(Arrays.asList(x, y));

                    possibleList.add(spot);
                }
            }
        }
        int currentIndex = 999;
        for (ArrayList<Integer> a : possibleList) {
            if (a.get(0).equals(this.i) && a.get(1).equals(this.j)) {
                currentIndex = possibleList.indexOf(a);
            }
        }
        if (currentIndex != 999) {
            possibleList.remove(currentIndex);
        }

        removeIndexes.clear();

        for (ArrayList<Integer> a : possibleList) {
            int x = a.get(0);
            int y = a.get(1);
            if (board[x][y].equals("-")) {
                removeIndexes.add(possibleList.indexOf(a));
            }
        }

        for (currentIndex = removeIndexes.size() - 1; currentIndex >= 0; currentIndex--) {
            int index = removeIndexes.get(currentIndex);
            possibleList.remove(index);
        }
        return possibleList;
    }

    /**
     * @return - x coordinate
     */
    @Override
    public int get_i() {
        return this.i;
    }

    /**
     * @return - y coordinate
     */
    @Override
    public int get_j() {
        return this.j;
    }

    /**
     * set x coordinate
     *
     * @param i - x coordinate
     */
    @Override
    public void set_i(int i) {
        this.i = i;
    }

    /**
     * set y coordinate
     *
     * @param j - y coordinate
     */
    @Override
    public void set_j(int j) {
        this.j = j;
    }

    /**
     * make a copy of king
     *
     * @return - new copied king
     */
    public King copy() {
        return new King(this.i, this.j);
    }

    /**
     * @return - king string
     */
    public String toString() {
        return "KING";
    }
}
