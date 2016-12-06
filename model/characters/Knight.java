package model.characters;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Knight class
 */
public class Knight implements Character {

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
    public Knight(int i, int j) {
        this.i = i;
        this.j = j;
    }

    /**
     * Generate all possible spot that knight can go to with given board
     *
     * @param board - model board
     * @return A list of Coordinate
     */
    @Override
    public ArrayList<ArrayList<Integer>> possibleMove(String[][] board) {
        ArrayList<ArrayList<Integer>> possibleList = new ArrayList<>();
        for (int x = this.i - 2; x <= this.i + 2; x++) {
            for (int y = this.j - 2; y <= this.j + 2; y++) {
                if (x >= 0 && x <= 3 && y >= 0 && y <= 3) {
                    ArrayList<Integer> spot = new ArrayList<>(Arrays.asList(x, y));

                    possibleList.add(spot);
                }
            }
        }
        ArrayList<Integer> removeIndexes = new ArrayList<>();
        for (ArrayList<Integer> a : possibleList) {
            if (a.get(0).equals(this.i - 2)) {
                int y = a.get(1);
                if (y == (this.j - 2) || y == (this.j) || y == (this.j + 2)) {
                    removeIndexes.add(possibleList.indexOf(a));
                }
            } else if (a.get(0).equals(this.i - 1)) {
                int y = a.get(1);
                if (y == (this.j - 1) || y == (this.j) || y == (this.j + 1)) {
                    removeIndexes.add(possibleList.indexOf(a));
                }
            } else if (a.get(0).equals(this.i)) {
                int y = a.get(1);
                if (y == (this.j - 2) || y == (this.j - 1) || y == (this.j) || y == (this.j + 1) || y == (this.j + 2)) {
                    removeIndexes.add(possibleList.indexOf(a));
                }
            } else if (a.get(0).equals(this.i + 1)) {
                int y = a.get(1);
                if (y == (this.j - 1) || y == (this.j) || y == (this.j + 1)) {
                    removeIndexes.add(possibleList.indexOf(a));
                }
            } else if (a.get(0).equals(this.i + 2)) {
                int y = a.get(1);
                if (y == (this.j - 2) || y == (this.j) || y == (this.j + 2)) {
                    removeIndexes.add(possibleList.indexOf(a));
                }

            }
        }
        for (int currentIndex = removeIndexes.size() - 1; currentIndex >= 0; currentIndex--) {
            int index = removeIndexes.get(currentIndex);
            possibleList.remove(index);
        }
        removeIndexes.clear();

        for (ArrayList<Integer> a : possibleList) {
            int x = a.get(0);
            int y = a.get(1);
            if (board[x][y].equals("-")) {
                removeIndexes.add(possibleList.indexOf(a));
            }
        }

        for (int currentIndex = removeIndexes.size() - 1; currentIndex >= 0; currentIndex--) {
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
     * set y coordinate
     *
     * @param i - y coordinate
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
     * make a copy of knight
     *
     * @return - new copied knight
     */
    public Knight copy() {
        return new Knight(this.i, this.j);
    }

    /**
     * @return - knight string
     */
    public String toString() {
        return "KNIGHT";
    }
}
