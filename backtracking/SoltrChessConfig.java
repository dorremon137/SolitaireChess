package backtracking;


import model.SoltrChessModel;
import model.characters.Character;


import java.util.ArrayList;
import java.util.Collection;

import static model.SoltrChessModel.CHARACTERS;
import static model.SoltrChessModel.DIMENSION;

/**
 * Created by Mungkorn on 11/25/16.
 */
public class SoltrChessConfig implements Configuration {
    private SoltrChessModel model;
    private String[][] board;
    private ArrayList<Character> characters;
    private int dimension = SoltrChessModel.DIMENSION;

    public SoltrChessConfig(SoltrChessModel model) {
        this.board = model.board;
        this.model = model;
        this.characters = new ArrayList<>(CHARACTERS);
    }

    public SoltrChessConfig(SoltrChessConfig copy) {
        this.dimension = copy.dimension;
        this.board = new String[copy.dimension][copy.dimension];
        this.characters = new ArrayList<>();
        for (Character c : copy.characters){
            this.characters.add(c.copy());
        }
        for (int row = 0; row < copy.dimension; row++) {
            System.arraycopy(copy.board[row], 0, this.board[row], 0, copy.dimension);}
    }

    @Override
    public Collection<Configuration> getSuccessors() {
        Collection<Configuration> successorsList = new ArrayList<>();
        ArrayList<Character> characters = this.characters;
        for (Character c : characters) {
            for (ArrayList<Integer> a : c.possibleMove(this.board)){
                int newX = a.get(0);
                int newY = a.get(1);
                if (!this.board[newX][newY].equals("-")){
                    SoltrChessConfig newConfig = new SoltrChessConfig(this);
                    int currentX = c.get_i();
                    int currentY = c.get_j();
                    newConfig.board[newX][newY] = newConfig.board[currentX][currentY];
                    newConfig.board[currentX][currentY] = "-";
                    int index = 999;
                    for (Character cha : newConfig.characters){
                        if (cha.get_i() == newX && cha.get_j() == newY){
                            index = newConfig.characters.indexOf(cha);
                            break;
                        }
                    }
                    if (index != 999){
                        newConfig.characters.remove(index);
                    }
                    for (Character cha : newConfig.characters){
                        if (cha.get_i() == currentX && cha.get_j() == currentY){
                            cha.set_i(newX);
                            cha.set_j(newY);
                            break;
                        }
                    }

                    successorsList.add(newConfig);
                }
            }
        }
        return  successorsList;
    }

    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public boolean isGoal() {
        return this.characters.size() == 1;
    }

    public String toString (){
        String boardString = "";
        for (int i = 0; i < DIMENSION; i++){
            for (int j = 0; j < DIMENSION; j++) {
                boardString += this.board[i][j] + " ";
            }
            boardString += "\n";
        }
        return boardString;
    }
}
