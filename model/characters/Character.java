package model.characters;

import java.util.ArrayList;

/**
 * Character Interface
 */
public interface Character {

    /**
     * Generate all possible spot that character can go to with given board
     *
     * @param board - model board
     * @return A list of Coordinate
     */
    public ArrayList<ArrayList<Integer>> possibleMove(String[][] board);

    /**
     * @return - x coordinate
     */
    public int get_i();

    /**
     * @return - y coordinate
     */
    public int get_j();

    /**
     * set x coordinate
     *
     * @param i - x coordinate
     */
    public void set_i(int i);

    /**
     * set y coordinate
     *
     * @param j - y coordinate
     */
    public void set_j(int j);

    /**
     * @return - Character string
     */
    public String toString();

    /**
     * make a copy of character
     *
     * @return - new copied character
     */
    public Character copy();

}
