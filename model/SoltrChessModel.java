package model;

import backtracking.Backtracker;
import backtracking.Configuration;
import backtracking.SoltrChessConfig;
import model.characters.Character;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Optional;

/**
 * Created by Mungkorn on 11/21/16.
 */
public class SoltrChessModel extends Observable {

    public static int DIMENSION = 4;
    public static ArrayList<Character> CHARACTERS = new ArrayList<>();
    public String[][] board;

    public SoltrChessModel(String[][] board) {
        this.board = board;
    }


    public void move(int sourceRow, int sourceCol, int destRow, int destCol) {
        Boolean hasCharacter = false;
        int indexOfCharacter = 999;
        for (Character c : CHARACTERS) {
            if (sourceRow == c.get_i() && sourceCol == c.get_j()) {
                hasCharacter = true;
                indexOfCharacter = CHARACTERS.indexOf(c);
                break;
            }
        }
        boolean validMove = false;
        if (hasCharacter & indexOfCharacter != 999) {
            Character c = CHARACTERS.get(indexOfCharacter);
            if (hasCharacter(destRow, destCol)) {
                for (ArrayList<Integer> a : c.possibleMove(this.board)) {
                    int x = a.get(0);
                    int y = a.get(1);
                    if (x == destRow && y == destCol) {
                        board[destRow][destCol] = board[sourceRow][sourceCol];
                        board[sourceRow][sourceCol] = "-";
                        int indexOfOldChar = 999;
                        for (Character oldC : CHARACTERS) {
                            if (destRow == oldC.get_i() && destCol == oldC.get_j()) {
                                indexOfOldChar = CHARACTERS.indexOf(oldC);
                                break;
                            }
                        }
                        if (indexOfOldChar != 999) {
                            CHARACTERS.remove(indexOfOldChar);
                        }
                        c.set_i(destRow);
                        c.set_j(destCol);
                        validMove = true;
                        announce("moveSuccess");
                        if (CHARACTERS.size() == 1) {
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

    public void newG() {
    }

    public void restart() {
    }

    public void hint() {
    }

    public List< Configuration > solve() {
        List<Configuration> pathList = new ArrayList<>();
        SoltrChessConfig config = new SoltrChessConfig(this);
        Backtracker tracker = new Backtracker();
        if (!tracker.solve(config).equals(Optional.empty())){
            pathList.addAll(tracker.solveWithPath(config));
        }
        return pathList;
    }

    public boolean hasCharacter(int i, int j) {
        return !this.board[i][j].equals("-");
    }

    private void announce(String arg) {
        setChanged();
        notifyObservers(arg);
    }

    public String[][] getBoard(){
        return this.board;
    }

}
