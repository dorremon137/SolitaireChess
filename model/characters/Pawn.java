package model.characters;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Mungkorn on 11/22/16.
 */
public class Pawn implements Character {

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
    public Pawn(int i, int j) {
        this.i = i;
        this.j = j;
    }

    /**
     * Generate all possible spot that character can go to with given board
     *
     * @param board - model board
     * @return A list of Coordinate
     */
    @Override
    public ArrayList<ArrayList<Integer>> possibleMove(String[][] board) {
        ArrayList<ArrayList<Integer>> possibleList = new ArrayList<>();
        ArrayList<Integer> spot = new ArrayList<>(Arrays.asList(this.i - 1, this.j - 1));
        possibleList.add(spot);
        spot = new ArrayList<>(Arrays.asList(this.i - 1, this.j + 1));
        possibleList.add(spot);
        ArrayList<Integer> removeIndexes = new ArrayList<>();
        for (ArrayList<Integer> a : possibleList) {
            int x = a.get(0);
            int y = a.get(1);
            if (x < 0 || x > 3 || y < 0 || y > 3) {
                removeIndexes.add(possibleList.indexOf(a));
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
    public Pawn copy() {
        return new Pawn(this.i, this.get_j());
    }

    /**
     * @return - pawn string
     */
    public String toString() {
        return "PAWN";
    }
}
