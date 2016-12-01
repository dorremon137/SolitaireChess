package model.characters;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import static model.SoltrChessModel.DIMENSION;

/**
 * Created by Mungkorn on 11/22/16.
 */
public class Rook implements Character {
    private int i;
    private int j;
    public Rook(int i, int j) {
        this.i = i;
        this.j = j;
    }

    @Override
    public ArrayList<ArrayList<Integer>> possibleMove(String[][] board) {
        ArrayList<ArrayList<Integer>> possibleList = new ArrayList<>();
        for (int x = this.i-3; x <= this.i+3; x++){
            for (int y = this.j-3; y <= this.j+3; y++) {
                if (x >= 0 && x <= 3 && y >= 0 && y <= 3) {
                    ArrayList<Integer> spot = new ArrayList<>(Arrays.asList(x, y));
                    possibleList.add(spot);
                }
            }
        }
        ArrayList<Integer> removeIndexes = new ArrayList<>();
        for (ArrayList<Integer> a : possibleList){
            if (a.get(0).equals(this.i - 3)){
                int y = a.get(1);
                if (y == (this.j - 3) || y == (this.j - 2) || y == (this.j-1)|| y == (this.j + 1) || y == (this.j + 2)
                        || y == (this.j + 3)){
                    removeIndexes.add(possibleList.indexOf(a));
                }
            } else if (a.get(0).equals(this.i - 2)){
                int y = a.get(1);
                if (y == (this.j - 3) || y == (this.j - 2) || y == (this.j-1)|| y == (this.j + 1) || y == (this.j + 2)
                        || y == (this.j + 3)){
                    removeIndexes.add(possibleList.indexOf(a));
                }
            } else if (a.get(0).equals(this.i - 1)) {
                int y = a.get(1);
                if (y == (this.j - 3) || y == (this.j - 2) || y == (this.j-1)|| y == (this.j + 1) || y == (this.j + 2)
                        || y == (this.j + 3)){
                    removeIndexes.add(possibleList.indexOf(a));
                }
            } else if (a.get(0).equals(this.i+1)){
                int y = a.get(1);
                if (y == (this.j - 3) || y == (this.j - 2) || y == (this.j-1)|| y == (this.j + 1) || y == (this.j + 2)
                        || y == (this.j + 3)){
                    removeIndexes.add(possibleList.indexOf(a));
                }
            } else if (a.get(0).equals(this.i + 2)){
                int y = a.get(1);
                if (y == (this.j - 3) || y == (this.j - 2) || y == (this.j-1)|| y == (this.j + 1) || y == (this.j + 2)
                        || y == (this.j + 3)){
                    removeIndexes.add(possibleList.indexOf(a));
                }
            } else if (a.get(0).equals(this.i + 3)){
                int y = a.get(1);
                if (y == (this.j - 3) || y == (this.j - 2) || y == (this.j-1)|| y == (this.j + 1) || y == (this.j + 2)
                        || y == (this.j + 3)){
                    removeIndexes.add(possibleList.indexOf(a));
                }

            } else if (a.get(0) == this.i && a.get(1) == this.j) {
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
            if (!board[x][y].equals("-")) {
                if (y == this.get_j()) {
                    if (x - this.i > 0) {
                        for (int index = DIMENSION - 1 - x; index <= DIMENSION - 1; index++) {
                            for (ArrayList<Integer> b : possibleList) {
                                int nextX = b.get(0);
                                int nextY = b.get(1);
                                if (nextX > x && nextY == this.get_j()) {
                                    removeIndexes.add(possibleList.indexOf(b));
                                }
                            }
                        }

                    } else if (x - this.i < 0) {
                        for (int index = DIMENSION - 1 - x; index >= 0; index--) {
                            for (ArrayList<Integer> b : possibleList) {
                                int nextX = b.get(0);
                                int nextY = b.get(1);
                                if (nextX < x && nextY == this.get_j()) {
                                    removeIndexes.add(possibleList.indexOf(b));
                                }
                            }
                        }
                    }
                }
                if (x == this.get_i()) {
                    if (y - this.j > 0) {
                        for (int index = DIMENSION - 1 - y; index <= DIMENSION - 1; index++) {
                            for (ArrayList<Integer> b : possibleList) {
                                int nextX = b.get(0);
                                int nextY = b.get(1);
                                if (nextY > y && nextX == this.get_i()) {
                                    removeIndexes.add(possibleList.indexOf(b));
                                }
                            }
                        }

                    } else if (y - this.j < 0) {
                        for (int index = DIMENSION - 1 - y; index >= 0; index--) {
                            for (ArrayList<Integer> b : possibleList) {
                                int nextX = b.get(0);
                                int nextY = b.get(1);
                                if (nextY < y && nextX == this.get_i()) {
                                    removeIndexes.add(possibleList.indexOf(b));
                                }
                            }
                        }
                    }
                }
            } else if (board[x][y].equals("-")) {
                removeIndexes.add(possibleList.indexOf(a));
            }
        }
        HashSet<Integer> removeDu = new HashSet<>();
        removeDu.addAll(removeIndexes);
        removeIndexes.clear();
        removeIndexes.addAll(removeDu);
        removeDu.clear();
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

    public Rook copy(){
        return new Rook(this.i, this.j);
    }

    public String toString(){
        return "ROOK (" + this.i + "," + this.j + ")";
    }
}
