package model.characters;

import java.util.ArrayList;

/**
 * Created by Mungkorn on 11/22/16.
 */
public interface Character {

    public ArrayList<ArrayList<Integer>> possibleMove(String[][] board);
    public int get_i();
    public int get_j();
    public void set_i(int i);
    public void set_j(int j);
    public String toString();
    public Character copy();

}
