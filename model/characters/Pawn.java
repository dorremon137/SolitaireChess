package model.characters;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Mungkorn on 11/22/16.
 */
public class Pawn implements Character {
    private int i;
    private int j;
    public Pawn(int i, int j) {
        this.i = i;
        this.j = j;
    }

    @Override
    public ArrayList<ArrayList<Integer>> possibleMove(String[][] board) {
        ArrayList<ArrayList<Integer>> possibleList = new ArrayList<>();
        ArrayList<Integer> spot = new ArrayList<>(Arrays.asList(this.i - 1, this.j - 1));
        possibleList.add(spot);
        spot = new ArrayList<>(Arrays.asList(this.i - 1, this.j + 1));
        possibleList.add(spot);
        ArrayList<Integer> removeIndexes = new ArrayList<>();
        for (ArrayList<Integer> a : possibleList){
            int x = a.get(0);
            int y = a.get(1);
            if (x < 0 || x > 3 || y < 0 || y > 3) {
                removeIndexes.add(possibleList.indexOf(a));
            }
        }
        for (int currentIndex = removeIndexes.size()-1; currentIndex >= 0 ; currentIndex--){
            int index = removeIndexes.get(currentIndex);
            possibleList.remove(index);
        }
        return possibleList;

    }

    @Override
    public int get_i() {
        return this.i;
    }

    @Override
    public int get_j() {
        return this.j;
    }

    @Override
    public void set_i(int i) {
        this.i = i;
    }

    @Override
    public void set_j(int j) {
        this.j = j;
    }

    public Pawn copy(){
        return new Pawn(this.i, this.get_j());
    }
    public String toString(){
        return "PAWN";
    }
}
