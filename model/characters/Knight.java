package model.characters;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Mungkorn on 11/22/16.
 */
public class Knight implements Character {

    private int i;
    private int j;
    public Knight(int i, int j) {
        this.i = i;
        this.j = j;
    }

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

    public Knight copy(){
        return new Knight(this.i,this.j);
    }
    public String toString(){
        return "KNIGHT";
    }
}
