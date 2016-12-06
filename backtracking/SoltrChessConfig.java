package backtracking;


import model.SoltrChessModel;
import model.characters.Character;

import java.util.ArrayList;
import java.util.Collection;

import static model.SoltrChessModel.DIMENSION;

/**
 * Configuration for backtracking
 */
public class SoltrChessConfig implements Configuration {

    private SoltrChessModel model;
    // SoltrChess Model

    private String[][] board;
    // the game board in 2D array format

    private ArrayList<Character> characters;
    //the character list

    private int dimension = SoltrChessModel.DIMENSION;
    // the board dimension

    /**
     * Constructor
     *
     * @param model - model
     */
    public SoltrChessConfig(SoltrChessModel model) {
        this.board = model.getBoard();
        this.model = model;
        this.characters = new ArrayList<>(model.characterList);
    }

    /**
     * Constructor for making copies
     *
     * @param copy - original config
     */
    public SoltrChessConfig(SoltrChessConfig copy) {
        this.dimension = copy.dimension;
        this.board = new String[copy.dimension][copy.dimension];
        this.characters = new ArrayList<>();
        for (Character c : copy.characters) {
            this.characters.add(c.copy());
        }
        for (int row = 0; row < copy.dimension; row++) {
            System.arraycopy(copy.board[row], 0, this.board[row], 0, copy.dimension);
        }
    }

    /**
     * Moves each character in character list of the config to its possible spot by create new config with moved already
     * been made.
     *
     * @return - a list of possible configuration
     */
    @Override
    public Collection<Configuration> getSuccessors() {
        Collection<Configuration> successorsList = new ArrayList<>();
        ArrayList<Character> characters = this.characters;
        for (Character c : characters) {
            for (ArrayList<Integer> a : c.possibleMove(this.board)) {
                int newX = a.get(0);
                int newY = a.get(1);
                if (!this.board[newX][newY].equals("-")) {
                    SoltrChessConfig newConfig = new SoltrChessConfig(this);
                    int currentX = c.get_i();
                    int currentY = c.get_j();
                    newConfig.board[newX][newY] = newConfig.board[currentX][currentY];
                    newConfig.board[currentX][currentY] = "-";
                    int index = 999;
                    for (Character cha : newConfig.characters) {
                        if (cha.get_i() == newX && cha.get_j() == newY) {
                            index = newConfig.characters.indexOf(cha);
                            break;
                        }
                    }
                    if (index != 999) {
                        newConfig.characters.remove(index);
                    }
                    for (Character cha : newConfig.characters) {
                        if (cha.get_i() == currentX && cha.get_j() == currentY) {
                            cha.set_i(newX);
                            cha.set_j(newY);
                            break;
                        }
                    }

                    successorsList.add(newConfig);
                }
            }
        }
        return successorsList;
    }

    /**
     * check if the move was valid. It always returns true because character.possibleMove already handle checking
     * whether it's possible to move to that spot
     *
     * @return - boolean true
     */
    @Override
    public boolean isValid() {
        return true;
    }

    /**
     * if one character left in the game, it's a goal
     *
     * @return - boolean true if goal, false otherwise
     */
    @Override
    public boolean isGoal() {
        return this.characters.size() == 1;
    }

    /**
     * make a string of the board in the correct format
     *
     * @return - string of correct format board
     */
    public String toString() {
        String boardString = "";
        for (int i = 0; i < DIMENSION; i++) {
            for (int j = 0; j < DIMENSION; j++) {
                boardString += this.board[i][j] + " ";
            }
            boardString += "\n";
        }
        return boardString;
    }

    /**
     * return board
     *
     * @return - board
     */
    public String[][] getBoard() {
        return this.board;
    }

    /**
     * return character list
     *
     * @return - character list
     */
    public ArrayList<Character> getList() {
        return this.characters;
    }
}
