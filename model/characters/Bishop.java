package model.characters;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import static model.SoltrChessModel.DIMENSION;

/**
 * Bishop Class
 */
public class Bishop implements Character {

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
    public Bishop(int i, int j) {
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
        for (int x = this.i - 3; x <= this.i + 3; x++) {
            for (int y = this.j - 3; y <= this.j + 3; y++) {
                if (x >= 0 && x <= 3 && y >= 0 && y <= 3) {
                    ArrayList<Integer> spot = new ArrayList<>(Arrays.asList(x, y));
                    possibleList.add(spot);
                }
            }
        }
        ArrayList<Integer> removeIndexes = new ArrayList<>();
        for (ArrayList<Integer> a : possibleList) {
            if (a.get(0).equals(this.i - 3)) {
                int y = a.get(1);
                if (y == (this.j - 2) || y == (this.j - 1) || y == (this.j) || y == (this.j + 1) || y == (this.j + 2)) {
                    removeIndexes.add(possibleList.indexOf(a));
                }
            } else if (a.get(0).equals(this.i - 2)) {
                int y = a.get(1);
                if (y == (this.j - 3) || y == (this.j - 1) || y == (this.j) || y == (this.j + 1) || y == (this.j + 3)) {
                    removeIndexes.add(possibleList.indexOf(a));
                }
            } else if (a.get(0).equals(this.i - 1)) {
                int y = a.get(1);
                if (y == (this.j - 3) || y == (this.j - 2) || y == (this.j) || y == (this.j + 2) || y == (this.j + 3)) {
                    removeIndexes.add(possibleList.indexOf(a));
                }
            } else if (a.get(0).equals(this.i)) {
                int y = a.get(1);
                if (y == (this.j - 3) || y == (this.j - 2) || y == (this.j - 1) || y == (this.j) || y == (this.j + 1) ||
                        y == (this.j + 2) || y == (this.j + 3)) {
                    removeIndexes.add(possibleList.indexOf(a));
                }
            } else if (a.get(0).equals(this.i + 1)) {
                int y = a.get(1);
                if (y == (this.j - 3) || y == (this.j - 2) || y == (this.j) || y == (this.j + 2) || y == (this.j + 3)) {
                    removeIndexes.add(possibleList.indexOf(a));
                }
            } else if (a.get(0).equals(this.i + 2)) {
                int y = a.get(1);
                if (y == (this.j - 3) || y == (this.j - 1) || y == (this.j) || y == (this.j + 1) || y == (this.j + 3)) {
                    removeIndexes.add(possibleList.indexOf(a));
                }
            } else if (a.get(0).equals(this.i + 3)) {
                int y = a.get(1);
                if (y == (this.j - 2) || y == (this.j - 1) || y == (this.j) || y == (this.j + 1) || y == (this.j + 2)) {
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
                if (x < this.i && y < this.j && Math.abs(this.i - x) == Math.abs(this.j - y)) {
                    for (int index = x - 1; index >= 0; index--) {
                        for (ArrayList<Integer> b : possibleList) {
                            int nextX = b.get(0);
                            int nextY = b.get(1);
                            if (nextX == index && nextY == index) {
                                removeIndexes.add(possibleList.indexOf(b));
                            } else if (nextX == index && nextY == y - index) {
                                removeIndexes.add(possibleList.indexOf(b));
                            } else if (nextX == index && nextY == y - 1) {
                                removeIndexes.add(possibleList.indexOf(b));
                            }
                        }
                    }
                } else if (x > this.i && y < this.j && Math.abs(this.i - x) == Math.abs(this.j - y)) {
                    for (int index = y - 1; index >= 0; index--) {
                        for (ArrayList<Integer> b : possibleList) {
                            int nextX = b.get(0);
                            int nextY = b.get(1);
                            if (nextX == DIMENSION - 1 - index && nextY == index) {
                                removeIndexes.add(possibleList.indexOf(b));
                            } else if (nextX == x + 1 && nextY == index) {
                                removeIndexes.add(possibleList.indexOf(b));
                            }
                        }
                    }
                } else if (x < this.i && y > this.j && Math.abs(this.i - x) == Math.abs(this.j - y)) {
                    for (int index = y + 1; index <= DIMENSION - 1; index++) {
                        for (ArrayList<Integer> b : possibleList) {
                            int nextX = b.get(0);
                            int nextY = b.get(1);
                            if (nextX == DIMENSION - 1 - index && nextY == index) {
                                removeIndexes.add(possibleList.indexOf(b));
                            } else if (nextX == x - 1 && nextY == index) {
                                removeIndexes.add(possibleList.indexOf(b));
                            }
                        }
                    }
                } else if (x > this.i && y > this.j && Math.abs(this.i - x) == Math.abs(this.j - y)) {
                    for (int index = x + 1; index <= DIMENSION - 1; index++) {
                        for (ArrayList<Integer> b : possibleList) {
                            int nextX = b.get(0);
                            int nextY = b.get(1);
                            if (nextX == index && nextY == index) {
                                removeIndexes.add(possibleList.indexOf(b));
                            } else if (nextX == index && nextY == index + 1) {
                                removeIndexes.add(possibleList.indexOf(b));
                            } else if (nextX == index && nextY == y + 1) {
                                removeIndexes.add(possibleList.indexOf(b));
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
     * make a copy of bishop
     *
     * @return - new copied bishop
     */
    public Bishop copy() {
        return new Bishop(this.i, this.j);
    }

    /**
     * @return - bishop string
     */
    public String toString() {
        return "BISHOP";
    }
}
