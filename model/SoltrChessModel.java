package model;

import backtracking.Backtracker;
import backtracking.Configuration;
import backtracking.SoltrChessConfig;
import model.characters.*;
import model.characters.Character;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * Created by Mungkorn on 11/21/16.
 */
public class SoltrChessModel extends Observable {

    public static int DIMENSION = 4;
    // board dimension

    public ArrayList<Character> characterList = new ArrayList<>();
    // a list of character

    private String[][] board;
    // board in 2D array format

    private List<Configuration> pathList = new ArrayList<>();
    // a list of solution in correct step order

    /**
     * Constructor the model
     *
     * @param fileName - string text file name
     * @throws FileNotFoundException - handle error
     */
    public SoltrChessModel(String fileName) throws FileNotFoundException {
        board = new String[DIMENSION][DIMENSION];
        Scanner f = new Scanner(new File(fileName));
        outerloop:
        for (int i = 0; i < DIMENSION; i++) {
            for (int j = 0; j < DIMENSION; j++) {
                board[i][j] = f.next();
                if (!board[i][j].equals("-")) {
                    switch (board[i][j]) {
                        case "B":
                            characterList.add(new Bishop(i, j));
                            break;
                        case "K":
                            characterList.add(new King(i, j));
                            break;
                        case "N":
                            characterList.add(new Knight(i, j));
                            break;
                        case "P":
                            characterList.add(new Pawn(i, j));
                            break;
                        case "Q":
                            characterList.add(new Queen(i, j));
                            break;
                        case "R":
                            characterList.add(new Rook(i, j));
                            break;
                    }
                }
            }
        }
        f.close();
        for (int i = 0; i < DIMENSION; i++) {
            for (int j = 0; j < DIMENSION; j++) {
                if (!(board[i][j].equals("B")) || !(board[i][j].equals("K")) || !(board[i][j].equals("N")) ||
                        !(board[i][j].equals("P")) || !(board[i][j].equals("Q")) || !(board[i][j].equals("R")) ||
                        !(board[i][j].equals("-"))) {
                    announce("InvalidFormat");
                }
            }
        }
        announce(null);
    }


    /**
     * move character from source to destination if it's movable.
     *
     * @param sourceRow - source x coordinate
     * @param sourceCol - source y coordinate
     * @param destRow   - destination x coordinate
     * @param destCol   - destination y coordinate
     */
    public void move(int sourceRow, int sourceCol, int destRow, int destCol) {
        Boolean hasCharacter = false;
        int indexOfCharacter = 999;
        for (Character c : this.characterList) {
            if (sourceRow == c.get_i() && sourceCol == c.get_j()) {
                hasCharacter = true;
                indexOfCharacter = this.characterList.indexOf(c);
                break;
            }
        }
        boolean validMove = false;
        if (hasCharacter & indexOfCharacter != 999) {
            Character c = this.characterList.get(indexOfCharacter);
            if (hasCharacter(destRow, destCol)) {
                for (ArrayList<Integer> a : c.possibleMove(this.board)) {
                    int x = a.get(0);
                    int y = a.get(1);
                    if (x == destRow && y == destCol) {
                        board[destRow][destCol] = board[sourceRow][sourceCol];
                        board[sourceRow][sourceCol] = "-";
                        int indexOfOldChar = 999;
                        for (Character oldC : this.characterList) {
                            if (destRow == oldC.get_i() && destCol == oldC.get_j()) {
                                indexOfOldChar = this.characterList.indexOf(oldC);
                                break;
                            }
                        }
                        if (indexOfOldChar != 999) {
                            this.characterList.remove(indexOfOldChar);
                        }
                        c.set_i(destRow);
                        c.set_j(destCol);
                        validMove = true;
                        announce("moveSuccess");
                        if (this.characterList.size() == 1) {
                            announce("finished");
                        }
                        break;
                    }
                }
                if (!validMove) {
                    announce("invalidMove");
                }
            } else {
                announce("noCharAtDest");
            }
        } else {
            announce("noCharAtSource");
        }

    }

    public void hint() {
        try {
            this.solve(false);
            Configuration hint = this.pathList.get(0);
            this.board = hint.getBoard();
            this.characterList = hint.getList();
            announce("hint");
            if (this.characterList.size() == 1) {
                announce("finished");
            }
        } catch (IndexOutOfBoundsException notSolvable) {
        }

    }

    public synchronized void solve(boolean showSteps) {
        this.pathList = new ArrayList<>();
        if (this.characterList.size() == 1) {
            announce("finished");
        } else {
            SoltrChessConfig config = new SoltrChessConfig(this);
            Backtracker tracker = new Backtracker();
            if (!tracker.solve(config).equals(Optional.empty())) {
                pathList.addAll(tracker.solveWithPath(config));

            }
            if (this.pathList.isEmpty()) {
                announce("notSolvable");
            } else {
                for (Configuration c : pathList) {
                    for (int i = 0; i < DIMENSION; i++) {
                        for (int j = 0; j < DIMENSION; j++) {
                            board[i][j] = c.getBoard()[i][j];
                        }
                    }
                    characterList = c.getList();
                    if (showSteps) {
                        setChanged();
                        notifyObservers("steps");
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }

            }
        }
    }

    /**
     * check the given coordinate of the board has character
     *
     * @param i - x coordinate
     * @param j - y coordinate
     * @return - true if it  has character, false otherwise
     */
    public boolean hasCharacter(int i, int j) {
        return !this.board[i][j].equals("-");
    }

    /**
     * notify observer
     *
     * @param arg - object
     */
    private void announce(String arg) {
        setChanged();
        notifyObservers(arg);

    }

    /**
     * return the board
     *
     * @return - board
     */
    public String[][] getBoard() {
        return this.board;
    }

    /**
     * return the character list
     *
     * @return - character list
     */
    public ArrayList<Character> getList() {
        return this.characterList;
    }

    /**
     * return solution path
     *
     * @return - path list
     */
    public List<Configuration> getPathList() {
        return this.pathList;
    }


}
