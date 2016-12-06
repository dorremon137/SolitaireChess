package ptui;

//import model.*;

import model.SoltrChessModel;
import model.characters.Character;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;

import static model.SoltrChessModel.DIMENSION;

//import java.io.FileNotFoundException;
//import java.util.stream.IntStream;

/**
 * Part of SoltrChess project.
 * Created 11 2015
 *
 * @author James Heliotis
 */
public class SoltrChessPTUI implements Observer {

    private SoltrChessModel model;
    // model (observable)

    private String gameTitle;
    // title of the game

    private Integer sourceRow;
    // coordinate of character

    private Integer sourceCol;
    // coordinate of character

    private Integer destRow;
    // coordinate of character

    private Integer destCol;
    // coordinate of character

    private Character currentCharacter;
    // chosen character

    private String[][] originalBoard = new String[DIMENSION][DIMENSION];
    // the original board before changes

    private ArrayList<Character> characters = new ArrayList<>();
    // character list

    private String fileName;
    // file name

    /**
     * Constructor
     *
     * @param fileName - game name
     */
    public SoltrChessPTUI(String fileName) {
        this.gameTitle = fileName.replace("data/", "");
        try {
            this.model = new SoltrChessModel(fileName);
        } catch (FileNotFoundException e) {
            System.out.println("Invalid file");
        }
        this.model.addObserver(this);
        for (Character c : model.getList()) {
            this.characters.add(c.copy());
        }
        this.fileName = fileName;
        for (int row = 0; row < DIMENSION; row++) {
            System.arraycopy(model.getBoard()[row], 0, originalBoard[row], 0, DIMENSION);
        }
    }


    // CONTROLLER
    public void run() throws FileNotFoundException {
        if (model.getList().size() == 1) {
            this.update(this.model, null);
            this.update(this.model, "finished");
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
                    this.model = new SoltrChessModel(fileName);
                    this.model.addObserver(this);
                    characters.clear();
                    for (Character c : model.getList()) {
                        this.characters.add(c.copy());
                    }
                    for (int row = 0; row < DIMENSION; row++) {
                        System.arraycopy(model.getBoard()[row], 0, originalBoard[row], 0, DIMENSION);
                    }
                    System.out.println("Restart the game");
                    this.run();

                } else if (words[0].startsWith("s")) {
                    this.model.solve(true);
                } else if (words[0].startsWith("h")) {
                    model.hint();
                } else if (words[0].startsWith("n")) {
                    String file = "data/";
                    System.out.print("game file name: ");
                    file += in.nextLine();
                    try {
                        SoltrChessPTUI newGame = new SoltrChessPTUI(file);
                        this.gameTitle = file.replace("data/", "");
                        System.out.println("New Game " + gameTitle);
                        newGame.run();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                } else if (words[0].startsWith("m")) {
                    System.out.print("Source row? ");
                    this.sourceRow = Integer.valueOf(in.nextLine());
                    System.out.print("Source col? ");
                    this.sourceCol = Integer.valueOf(in.nextLine());
                    System.out.print("dest row? ");
                    this.destRow = Integer.valueOf(in.nextLine());
                    System.out.print("dest col? ");
                    this.destCol = Integer.valueOf(in.nextLine());
                    for (Character c : model.getList()) {
                        if (c.get_i() == sourceRow && c.get_j() == sourceCol) {
                            currentCharacter = c;
                            break;
                        }
                    }
                    model.move(sourceRow, sourceCol, destRow, destCol);
                }
            }
        }

    }

    /**
     * update the ui according to the model
     *
     * @param o   - model
     * @param arg - object
     */
    @Override
    public synchronized void update(Observable o, Object arg) {
        if (arg != null && arg.toString().equals("moveSuccess")) {
            System.out.println(currentCharacter.toString() + " to (" + destRow
                    + "," + destCol + ")");
            String boardString = "";
            for (int i = 0; i < DIMENSION; i++) {
                for (int j = 0; j < DIMENSION; j++) {
                    boardString += this.model.getBoard()[i][j] + " ";
                }
                boardString += "\n";
            }
            System.out.print(boardString);
        } else if (arg != null && arg.toString().equals("steps")) {
            if (!this.model.getPathList().isEmpty()) {
                System.out.println("STEP " + (this.characters.size() - model.characterList.size()));
                String boardString = "";
                for (int i = 0; i < DIMENSION; i++) {
                    for (int j = 0; j < DIMENSION; j++) {
                        boardString += this.model.getBoard()[i][j] + " ";
                    }
                    boardString += "\n";
                }
                System.out.println(boardString);
                if (model.characterList.size() == 1) {
                    System.out.println("You won. Congratulations!");
                }
            }
        } else if (arg != null && arg.toString().equals("noCharAtDest")) {
            System.out.println("Cannot move " + currentCharacter.toString() +
                    " to an empty spot (" + destRow + "," + destCol + ")");
        } else if (arg != null && arg.toString().equals("noCharAtSource")) {
            System.out.println("Cannot choose character from an empty spot ("
                    + sourceRow + "," + sourceCol + ")");
        } else if (arg != null && arg.toString().equals("invalidMove")) {
            System.out.println("Invalid move for " + currentCharacter.toString());
        } else if (arg != null && arg.toString().equals("notSolvable")) {
            System.out.println("This game is not solvable, no solution");
        } else if (arg != null && arg.toString().equals("InvalidFormat")) {
            System.out.println("Invalid board format");
        } else if (arg != null && arg.toString().equals("hint")) {
            String boardString = "";
            for (int i = 0; i < DIMENSION; i++) {
                for (int j = 0; j < DIMENSION; j++) {
                    boardString += this.model.getBoard()[i][j] + " ";
                }
                boardString += "\n";
            }
            System.out.print(boardString);
        } else if (arg == null) {
            String boardString = "";
            for (int i = 0; i < DIMENSION; i++) {
                for (int j = 0; j < DIMENSION; j++) {
                    boardString += this.model.getBoard()[i][j] + " ";
                }
                boardString += "\n";
            }
            System.out.print(boardString);
        }
        if (arg != null && arg.toString().equals("finished")) {
            System.out.println("You won. Congratulations!");
        }
    }
}
