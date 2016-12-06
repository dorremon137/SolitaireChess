/**
 * Part of SoltrChessLayout project.
 * Created 10 2015
 *
 * @author James Heliotis
 */

package gui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.effect.Glow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.SoltrChessModel;
import model.characters.Character;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

import static model.SoltrChessModel.DIMENSION;

/**
 * A miniature chess board
 *
 * @author James Heliotis
 */
public class SoltrChessGUI extends Application implements Observer {

    private String[][] originalBoard;
    // the original board of game in 2D array format

    private SoltrChessModel model;
    // Solterchess model (Observable)

    private HashMap<String, Image> chessCollection = new HashMap<>();
    // images collection for chessmen

    private int dimension = DIMENSION;
    // demension of the board

    private ArrayList<Character> characters = new ArrayList<>();
    // a list of character in the game

    private BorderPane pane;
    // the main pane

    private double windowH = 520.00;
    // Scene's Height

    private double windowW = 480.00;
    // Scene's Width

    private double gridH = windowH - 40;
    // Grid's Height

    private double gridW = windowW;
    // Grid's Width

    private ArrayList<Button> buttonsList = new ArrayList<>();
    // a list of buttons in the BorderPane

    private Text top;
    // text at the top

    private GridPane gridBoard;
    // grid pane (center of border pane)

    private static int sourceRow = 999;
    // coordinate of character (Implemented as Static for lambda implementation)

    private static int sourceCol = 999;
    // coordinate of character (Implemented as Static for lambda implementation)

    private static int destRow = 999;
    // coordinate of character (Implemented as Static for lambda implementation)

    private static int destCol = 999;
    // coordinate of character (Implemented as Static for lambda implementation)

    private static Character currentCharacter;
    // chosen character (Implemented as Static for lambda implementation)

    private Button newGame;
    // new game button

    private Button restart;
    // restart button

    private Button hint;
    // hint button

    private Button solve;
    // solve button

    private String sceneTitle;
    // game title

    private String fileName;
    // filename

    public SoltrChessGUI() throws Exception {
    }
    // GUI constructor

    /**
     * Initialize the game
     *
     * @throws Exception
     */
    public void init() throws Exception {
        sourceRow = 999;
        sourceCol = 999;
        destRow = 999;
        destCol = 999;
        currentCharacter = null;
        this.originalBoard = new String[dimension][dimension];
        String fileName = getParameters().getRaw().get(0);


        String hover = "#myComboBox:hover {-fx-color: -fx-base;}";

        this.sceneTitle = fileName.replace("data/", "");
        try {
            this.model = new SoltrChessModel(fileName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        this.model.addObserver(this);
        for (Character c : model.getList()) {
            this.characters.add(c.copy());
        }
        this.fileName = fileName;
        for (int row = 0; row < DIMENSION; row++) {
            System.arraycopy(model.getBoard()[row], 0, originalBoard[row], 0, DIMENSION);
        }
        Image bishop = new Image(getClass().getResourceAsStream("resources/bishop.png"));
        Image knight = new Image(getClass().getResourceAsStream("resources/knight.png"));
        Image pawn = new Image(getClass().getResourceAsStream("resources/pawn.png"));
        Image queen = new Image(getClass().getResourceAsStream("resources/queen.png"));
        Image king = new Image(getClass().getResourceAsStream("resources/king.png"));
        Image rook = new Image(getClass().getResourceAsStream("resources/rook.png"));
        Image empty = new Image(getClass().getResourceAsStream("resources/empty.png"));
        chessCollection.put("B", bishop);
        chessCollection.put("N", knight);
        chessCollection.put("P", pawn);
        chessCollection.put("Q", queen);
        chessCollection.put("K", king);
        chessCollection.put("R", rook);
        chessCollection.put("-", empty);
        gridBoard = new GridPane();
        pane = new BorderPane();
        pane.setCenter(gridBoard);
        gridBoard.setPrefHeight(gridH);
        gridBoard.setPrefWidth(gridW);
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                if (i == 0 || i == 2) {
                    if (j == 0 || j == 2) {
                        Button b = new Button();
                        b.setPrefHeight(gridH / 4);
                        b.setPrefWidth(gridW / 4);
                        b.setBorder(Border.EMPTY);
                        Image light = new Image(getClass().getResourceAsStream("resources/light.png"));
                        BackgroundImage backgroundImg = new BackgroundImage(light, BackgroundRepeat.NO_REPEAT,
                                BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
                        Background background = new Background(backgroundImg);
                        b.setBackground(background);
                        b.setPadding(new Insets(0, 0, 0, 0));
                        buttonsList.add(b);
                        gridBoard.add(b, j, i);
                    } else if (j == 1 || j == 3) {
                        Button b = new Button();
                        b.setPrefHeight(gridH / 4);
                        b.setPrefWidth(gridW / 4);
                        b.setBorder(Border.EMPTY);
                        Image dark = new Image(getClass().getResourceAsStream("resources/dark.png"));
                        BackgroundImage backgroundImg = new BackgroundImage(dark, BackgroundRepeat.NO_REPEAT,
                                BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
                        Background background = new Background(backgroundImg);
                        b.setBackground(background);
                        b.setPadding(new Insets(0, 0, 0, 0));
                        buttonsList.add(b);
                        gridBoard.add(b, j, i);
                    }

                } else if (i == 1 || i == 3) {
                    if (j == 1 || j == 3) {
                        Button b = new Button();
                        b.setPrefHeight(gridH / 4);
                        b.setPrefWidth(gridW / 4);
                        b.setBorder(Border.EMPTY);
                        Image light = new Image(getClass().getResourceAsStream("resources/light.png"));
                        BackgroundImage backgroundImg = new BackgroundImage(light, BackgroundRepeat.NO_REPEAT,
                                BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
                        Background background = new Background(backgroundImg);
                        b.setBackground(background);
                        b.setPadding(new Insets(0, 0, 0, 0));
                        buttonsList.add(b);
                        gridBoard.add(b, j, i);
                    } else if (j == 0 || j == 2) {
                        Button b = new Button();
                        b.setPrefHeight(gridH / 4);
                        b.setBorder(Border.EMPTY);
                        b.setPrefWidth(gridW / 4);
                        Image dark = new Image(getClass().getResourceAsStream("resources/dark.png"));
                        BackgroundImage backgroundImg = new BackgroundImage(dark, BackgroundRepeat.NO_REPEAT,
                                BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
                        Background background = new Background(backgroundImg);
                        b.setBackground(background);
                        b.setPadding(new Insets(0, 0, 0, 0));
                        buttonsList.add(b);
                        gridBoard.add(b, j, i);
                    }
                }
            }
        }
        gridBoard.setPadding(new Insets(0, 0, 0, 0));
        top = new Text("Select a chessman");
        top.prefHeight(20.00);
        top.minHeight(20.00);
        top.setTextAlignment(TextAlignment.CENTER);
        pane.setTop(top);
        newGame = new Button("New Game");
        newGame.setPrefHeight(20);
        newGame.setStyle(hover);
        restart = new Button("Restart");
        restart.setPrefHeight(20);
        restart.setStyle(hover);
        hint = new Button("Hint");
        hint.setPrefHeight(20);
        hint.setStyle(hover);
        solve = new Button("Solve");
        solve.setPrefHeight(20);
        solve.setStyle(hover);
        hint.setStyle(hover);
        restart.setStyle(hover);
        HBox bottom = new HBox();
        bottom.setMaxHeight(20.00);
        bottom.setPrefHeight(20.00);
        bottom.getChildren().addAll(newGame, restart, hint, solve);
        bottom.setAlignment(Pos.CENTER);
        pane.setBottom(bottom);
        update(this.model, null);
    }

    /**
     * update the GUI based on the model
     * using Platform.runLater() for thread in solve to work
     *
     * @param o   - model
     * @param arg - object
     */
    public void update(Observable o, Object arg) {
        Platform.runLater(() -> updateMethod(o, arg));
    }


    /**
     * the actual update method
     *
     * @param o   - model
     * @param arg - object
     */
    public void updateMethod(Observable o, Object arg) {
        boolean changed = false;
        int index = 0;
        if (arg != null && arg.toString().equals("steps")) {
            if (!this.model.getPathList().isEmpty()) {
                changed = true;
                top.setText("STEP " + (characters.size() - model.characterList.size()));
                index = 0;
                for (int i = 0; i < dimension; i++) {
                    for (int j = 0; j < dimension; j++) {
                        buttonsList.get(index).setGraphic(new ImageView(chessCollection.get(model.getBoard()[i][j])));
                        index++;
                    }
                }
            }
        }
        if (!changed) {
            index = 0;
            for (int i = 0; i < dimension; i++) {
                for (int j = 0; j < dimension; j++) {
                    buttonsList.get(index).setGraphic(new ImageView(chessCollection.get(model.getBoard()[i][j])));
                    index++;
                }
            }
        }
        if (sourceRow == 999 && sourceCol == 999) {
            top.setText("Select a chessman");
        } else {
            top.setText("Select another chessman to be captured");
        }
        if (arg != null && arg.toString().equals("moveSuccess")) {
            top.setText(currentCharacter.toString() + " to (" + destRow
                    + "," + destCol + "). Select a chessman");
            sourceRow = 999;
            sourceCol = 999;
            destRow = 999;
            destCol = 999;
            currentCharacter = null;
        } else if (arg != null && arg.toString().equals("steps")) {
            if (!this.model.getPathList().isEmpty()) {
                top.setText("STEP " + (characters.size() - model.characterList.size()));
                index = 0;
                for (int i = 0; i < dimension; i++) {
                    for (int j = 0; j < dimension; j++) {
                        buttonsList.get(index).setGraphic(new ImageView(chessCollection.get(model.getBoard()[i][j])));
                        index++;
                    }
                }
            }
        } else if (arg != null && arg.toString().equals("noCharAtDest")) {
            top.setText("Cannot move " + currentCharacter.toString() +
                    " to an empty spot (" + destRow + "," + destCol + "). Select a chessman");
            sourceRow = 999;
            sourceCol = 999;
            destRow = 999;
            destCol = 999;
            currentCharacter = null;
        } else if (arg != null && arg.toString().equals("noCharAtSource")) {
            top.setText("Cannot choose character from an empty spot ("
                    + sourceRow + "," + sourceCol + "). Select a chessman");
            sourceRow = 999;
            sourceCol = 999;
            destRow = 999;
            destCol = 999;
            currentCharacter = null;
        } else if (arg != null && arg.toString().equals("invalidMove")) {
            top.setText("Invalid move for " + currentCharacter.toString() + ". Select a chessman");
            sourceRow = 999;
            sourceCol = 999;
            destRow = 999;
            destCol = 999;
            currentCharacter = null;
        } else if (arg != null && arg.toString().equals("notSolvable")) {
            top.setText("This game is not solvable, no solution");
        } else if (arg != null && arg.toString().equals("hint")) {
            top.setText("Hint was processed, continue select a chessman");
        }
        if (arg != null && arg.toString().equals("finished")) {
            top.setText("You won. Congratulations!");
        }
        for (Button b : buttonsList) {
            b.setEffect(null);
        }
    }

    public void error() {
        Stage error = new Stage();
        error.setTitle("Error");
        Text message = new Text("Invalid file");
        BorderPane pane = new BorderPane();
        pane.setCenter(message);
        Scene scene = new Scene(pane);
        pane.setPrefHeight(120);
        pane.setPrefWidth(120);
        error.setScene(scene);
        error.show();
    }

    /**
     * Construct the layout for the game.
     *
     * @param stage container (window) in which to render the UI
     */


    public void start(Stage stage) {
        int index = 0;
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                Button b = buttonsList.get(index);
                int finalI = i;
                int finalJ = j;
                b.setOnAction((ActionEvent event) -> {
                    b.setEffect(new InnerShadow(50, Color.BLANCHEDALMOND));
                    if (sourceRow == 999 && sourceCol == 999) {
                        sourceRow = finalI;
                        sourceCol = finalJ;
                        Character curr = null;
                        for (Character c : model.characterList) {
                            if (c.get_i() == sourceRow && c.get_j() == sourceCol) {
                                curr = c;
                                break;
                            }
                        }
                        int nextIndex = 0;
                        int x = 999;
                        int y = 999;
                        if (curr != null) {
                            for (int nextX = 0; nextX < dimension; nextX++) {
                                for (int nextY = 0; nextY < dimension; nextY++) {
                                    Boolean hasEffect = false;
                                    for (ArrayList<Integer> c : curr.possibleMove(model.getBoard())) {
                                        int tempX = c.get(0);
                                        int tempY = c.get(1);
                                        if (nextX == tempX && nextY == tempY) {
                                            x = tempX;
                                            y = tempY;
                                            hasEffect = true;
                                            buttonsList.get(nextIndex).setEffect(new Glow(0.5));
                                            break;
                                        }

                                    }
                                    if (!hasEffect) {
                                        buttonsList.get(nextIndex).setEffect(new GaussianBlur());
                                    }
                                    nextIndex++;
                                }
                            }

                            if (curr.possibleMove(model.getBoard()).size() == 0) {
                                for (Button but : buttonsList) {
                                    if (!but.equals(b)) {
                                        but.setEffect(new GaussianBlur());
                                    }
                                }
                            }
                        }
                    } else {
                        destRow = finalI;
                        destCol = finalJ;
                    }
                    if (sourceRow != 999 && sourceCol != 999 && destRow != 999 && destCol != 999) {
                        for (Character c : model.characterList) {
                            if (sourceRow == c.get_i() && sourceCol == c.get_j()) {
                                currentCharacter = c;
                                break;
                            }
                        }
                        model.move(sourceRow, sourceCol, destRow, destCol);
                    }
                    b.setEffect(new InnerShadow(50, Color.BLANCHEDALMOND));
                });
                index++;
            }
        }
        solve.setOnAction(e -> {
            Thread t = new Thread(() -> this.model.solve(true));
            t.start();
        });

        newGame.setOnAction(e -> {
            try {
                FileChooser chooser = new FileChooser();
                chooser.setTitle("Choose new game");
                File newDict = new File("./data/");
                chooser.setInitialDirectory(newDict);
                File newFile = chooser.showOpenDialog(stage);
                String fileName = "data/" + newFile.getName();
                this.model = new SoltrChessModel(fileName);
                this.characters = this.model.characterList;
                for (int row = 0; row < DIMENSION; row++) {
                    System.arraycopy(model.getBoard()[row], 0, originalBoard[row], 0, DIMENSION);
                }
                this.fileName = fileName;
                stage.setTitle(newFile.getName());
                model.addObserver(this);
                update(model, null);
            } catch (FileNotFoundException e1) {
                error();
            } catch (NullPointerException e2) {

            }
        });
        restart.setOnAction(e -> {
            sourceRow = 999;
            sourceCol = 999;
            destRow = 999;
            destCol = 999;
            currentCharacter = null;
            try {
                this.model = new SoltrChessModel(fileName);
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            }
            this.model.addObserver(this);
            characters.clear();
            for (Character c : model.getList()) {
                this.characters.add(c.copy());
            }
            for (int row = 0; row < DIMENSION; row++) {
                System.arraycopy(model.getBoard()[row], 0, originalBoard[row], 0, DIMENSION);
            }
            update(this.model, null);


        });

        hint.setOnAction(e -> {
            new Thread(() -> model.hint()).start();
        });
        Scene scene = new Scene(pane);
        pane.maxHeight(windowH);
        pane.maxWidth(windowW);
        gridBoard.minHeight(120 * 4);
        gridBoard.maxWidth(120 * 4);
        stage.getHeight();
        stage.setMaxHeight(windowH + 25);
        stage.setMaxWidth(windowW);
        stage.setMinHeight(windowH + 25);
        stage.setMinWidth(windowW);
        stage.setScene(scene);
        stage.setResizable(true);
        stage.setTitle(sceneTitle);
        stage.show();

    }

    /**
     * launch the program
     *
     * @param args - game text file
     */
    public static void main(String[] args) {
        Application.launch(args);
    }
}
