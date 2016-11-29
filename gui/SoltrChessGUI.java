/**
 * Part of SoltrChessLayout project.
 * Created 10 2015
 *
 * @author James Heliotis
 */

package gui;

import javafx.application.Application;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import model.SoltrChessModel;
import model.characters.*;
import model.characters.Character;

import java.io.File;
import java.util.*;

import static model.SoltrChessModel.DIMENSION;

/**
 * A miniature chess board
 *
 * @author James Heliotis
 */
public class SoltrChessGUI extends Application implements Observer{
    private String[][] board;
    private SoltrChessModel model;
    private HashMap<String, Image> chessCollection;
    private int dimension = DIMENSION;
    private ArrayList<Character> characters = new ArrayList<>();

    public SoltrChessGUI()throws Exception{
        this.board = new String[dimension][dimension];
        Parameters fileName = this.getParameters();
        Scanner f = new Scanner(new File(String.valueOf(fileName)));
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                this.board[i][j] = f.next();
                if (!this.board[i][j].equals("-")) {
                    switch (this.board[i][j]) {
                        case "B":
                            this.characters.add(new Bishop(i,j));
                            break;
                        case "K":
                            this.characters.add(new King(i,j));
                            break;
                        case "N":
                            this.characters.add(new Knight(i,j));
                            break;
                        case "P":
                            this.characters.add(new Pawn(i,j));
                            break;
                        case "Q":
                            this.characters.add(new Queen(i,j));
                            break;
                        case "R":
                            this.characters.add(new Rook(i,j));
                            break;
                    }
                }
            }
        }
        f.close();
        model = new SoltrChessModel(board);
        model.addObserver(this);


    }
    public void init() throws Exception{
        Image bishop = new Image(getClass().getResourceAsStream("resources/bishop.png"));
        Image knight = new Image(getClass().getResourceAsStream("resources/knight.png"));
        Image pawn = new Image(getClass().getResourceAsStream("resources/pawn.png"));
        Image queen = new Image(getClass().getResourceAsStream("resources/queen.png"));
        Image king = new Image(getClass().getResourceAsStream("resources/king.png"));
        Image rook = new Image(getClass().getResourceAsStream("resources/rook.png"));
        chessCollection.put("B",bishop);
        chessCollection.put("N",knight);
        chessCollection.put("P",pawn);
        chessCollection.put("Q",queen);
        chessCollection.put("K",king);
        chessCollection.put("R",rook);



    }
    public void update(Observable o, Object arg){


    }
    /**
     * Construct the layout for the game.
     *
     * @param stage container (window) in which to render the UI
     *
     */
    public void start( Stage stage ) {

    }
    public static void main(String[] args){Application.launch(args);}
}
