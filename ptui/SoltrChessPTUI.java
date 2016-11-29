package ptui;

//import model.*;

import backtracking.Configuration;
import model.SoltrChessModel;
import model.characters.*;
import model.characters.Character;

import static model.SoltrChessModel.CHARACTERS;
import static model.SoltrChessModel.DIMENSION;

//import java.io.FileNotFoundException;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
//import java.util.stream.IntStream;

/**
 * Part of SoltrChess project.
 * Created 11 2015
 *
 * @author James Heliotis
 */
public class SoltrChessPTUI implements Observer {

    public static SoltrChessModel model;
    private static int dimension = DIMENSION;
    private String[][] board;
    private ArrayList<Character> characters = CHARACTERS;
    private String gameTitle;
    private Integer sourceRow;
    private Integer sourceCol;
    private Integer destRow;
    private Integer destCol;
    private Character currentCharacter;
    private List<Configuration> pathList = new ArrayList<>();

    public SoltrChessPTUI( String fileName ) throws FileNotFoundException {
        this.gameTitle = fileName;
        this.gameTitle = this.gameTitle.replace("data/", "");
        this.board = new String[dimension][dimension];
        Scanner f = new Scanner(new File(fileName));
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


    // CONTROLLER
    public void run() throws FileNotFoundException {
        if (CHARACTERS.size() == 1){
            this.update(this.model,"finished");
        } else {
            this.update(this.model, null);
        }
        Scanner in = new Scanner(System.in);
        for (; ; ) {
            System.out.print("[move,new,restart,hint,solve,quit]> ");
            String line = in.nextLine();
            String[] words = line.split("\\s+");
            if (words.length > 0) {
                if (words[0].startsWith("q")) {
                    break;
                } else if (words[0].startsWith("r")) {
                    this.model.restart();
                } else if (words[0].startsWith("s")) {
                    pathList = this.model.solve();
                    if (!pathList.isEmpty()) {
                        for (int i = 0; i < pathList.size(); i++) {
                            System.out.println("STEP " + (i + 1));
                            System.out.println(pathList.get(i));
                        }
                    }
                } else if (words[0].startsWith("h")) {
                    this.model.hint();
                } else if (words[0].startsWith("n")) {
                    String file = "data/";
                    System.out.print("game file name: ");
                    file += in.nextLine();
                    try {
                        SoltrChessPTUI newGame = new SoltrChessPTUI(file);
                        this.gameTitle = file.replace("data/","");
                        System.out.println("New Game " + gameTitle);
                        newGame.run();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    this.model.newG();
                } else if (words[0].startsWith("m")) {
                    System.out.print("Source row? ");
                    this.sourceRow = Integer.valueOf(in.nextLine());
                    System.out.print("Source col? ");
                    this.sourceCol = Integer.valueOf(in.nextLine());
                    System.out.print("dest row? ");
                    this.destRow = Integer.valueOf(in.nextLine());
                    System.out.print("dest col? ");
                    this.destCol = Integer.valueOf(in.nextLine());
                    for (Character c : characters){
                        if (c.get_i() == sourceRow && c.get_j() == sourceCol){
                            currentCharacter = c;
                            break;
                        }
                    }
                    model.move(sourceRow,sourceCol,destRow,destCol);
                }
            }
        }

    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg != null && arg.toString().equals("moveSuccess")){
            System.out.println(currentCharacter.toString() + " to (" + destRow
                    + "," + destCol + ")");
            String boardString = "";
            for (int i = 0; i < DIMENSION; i++){
                for (int j = 0; j < DIMENSION; j++) {
                    boardString += this.board[i][j] + " ";
                }
                boardString += "\n";
            }
            System.out.print(boardString);
        } else if (arg != null && arg.toString().equals("noCharAtDest")){
            System.out.println("Cannot move " + currentCharacter.toString() +
                    " to an empty spot (" + destRow + "," + destCol + ")");
        } else if (arg != null && arg.toString().equals("noCharAtSource")){
            System.out.println("Cannot choose character from an empty spot ("
                    + sourceRow + "," + sourceCol + ")");
        } else if (arg != null && arg.toString().equals("invalidMove")){
            System.out.println("Invalid move for " + currentCharacter.toString());
        } else if (arg == null){
            String boardString = "";
            for (int i = 0; i < DIMENSION; i++){
                for (int j = 0; j < DIMENSION; j++) {
                    boardString += this.board[i][j] + " ";
                }
                boardString += "\n";
            }
            System.out.print(boardString);
        }
        if (arg != null && arg.toString().equals("finished")){
            System.out.println("You won. Congratulations!");
        }
    }


    // VIEW
}
