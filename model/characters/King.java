package model.characters;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Mungkorn on 11/22/16.
 */
public class King implements Character {
    private int i;
    private int j;
    public King(int i, int j) {
        this.i = i;
        this.j = j;
    }


    @Override
    public ArrayList<ArrayList<Integer>> possibleMove(String[][] board) {
        ArrayList<ArrayList<Integer>> possibleList = new ArrayList<>();
        for (int x = this.i-1; x <= this.i+1; x++){
                for (int y = this.j-1; y <= this.j+1;y++) {
                    if (x >= 0 && x <= 3 && y >= 0 && y <= 3) {
                        ArrayList<Integer> spot = new ArrayList<>(Arrays.asList(x, y));

                        possibleList.add(spot);
                    }
                }
        }
        int currentIndex = 999;
        for (ArrayList<Integer> a : possibleList){
            if (a.get(0).equals(this.i) && a.get(1).equals(this.j)){
                currentIndex = possibleList.indexOf(a);
            }
        }
        if (currentIndex != 999){
            possibleList.remove(currentIndex);
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

    public King copy(){
        return new King(this.i,this.j);
    }


    public String toString(){
        return "KING";
    }
}
