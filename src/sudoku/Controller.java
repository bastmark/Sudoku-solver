package sudoku;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import java.io.*;
import java.util.Objects;
import java.util.Scanner;

/**
 * Controller class containing methods for controlling JavaFX objects.
 *
 * This class is passed as controller by sudoku.fxml when initializing root in Main class.
 *
 * @author Johannes Bastmark
 * @author Szymon Stypa
 * @author Axel Domell
 *
 * @version 1.0
 * @see <a href="https://github.com/bastmark/Sudoku-solver">Github repository</a>
 */
public class Controller {
    private Sudoku game;

    @FXML
    private GridPane grid;
    @FXML
    private Button solveButton;
    @FXML
    private Button clearButton;
    @FXML
    private Button openButton;

    /**
     * Controller constructor.
     */
    public Controller() {
    }

    /**
     * Initializes the controller.
     * Creates a Sudoku object and maps grid elements to the Sudoku table.
     * JavaFX button actions are implemented.
     */
    public void initialize() {
        game = new Sudoku();

        // Initialise all the TextField inputs and set on change listener to update game instance
        for (int i = 0; i < game.size(); i++) {
            for (int j = 0; j < game.size(); j++) {
                final int row = i;
                final int col = j;

                TextField field = new NumberTextField();
                field.setAlignment(Pos.CENTER);
                field.setPrefSize(100, 100);
                field.setMinWidth(20);
                field.setMinHeight(20);

                // Lite fulkod
                field.setStyle("-fx-background-color: #d7efe3;");
                if (i < 3 || i > 5) {
                    if (j < 3 || j > 5) field.setStyle("-fx-background-color: #96e0bb;");
                } else {
                    if (j > 2 && j < 6) field.setStyle("-fx-background-color: #96e0bb;");
                }

                field.textProperty().addListener((observable, oldValue, newValue) ->
                        game.insert(newValue.equals("") ? 0 : Integer.valueOf(newValue), row, col));

                grid.add(field, col, row);
            }
        }
        // Clear all the fields. Note that the Sudoku instance will be updated because of the connection above.
        clearButton.setOnAction(x -> grid.getChildren().forEach(child -> {
            if (child instanceof  NumberTextField) ((NumberTextField) child).setText("");
        }));

        // Solve the game and update all the TextFields in grid with corresponding values
        solveButton.setOnAction(x -> {
            if (game.solve()) {
                for (int i = 0; i < game.size(); i++) {
                    for (int j = 0; j < game.size(); j++) {
                        int value = game.get(i, j);
                        Objects.requireNonNull(getTextField(i, j, grid)).setText(value != 0 ? String.valueOf(value) : "");
                    }
                }
            }
        });

        // Opens FileChooser dialog and calls load file with selected .txt file
        openButton.setOnAction(x -> {
            FileChooser fileChooser = new FileChooser();

            // Set extension filter to open .txt files only
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
            fileChooser.getExtensionFilters().add(extFilter);

            // Show open file dialog
            File file = fileChooser.showOpenDialog(openButton.getScene().getWindow());

            // Call loadFile with opened file
            if(file != null){
                loadFile(file);
            }
        });

    }

    /**
     * Updates grid elements NumberTextField to corresponding value in file.
     * @param file is a .txt file containing digits separated by spaces corresponding to a sudoku puzzle
     */
    private void loadFile(File file){
        try (Scanner scanner = new Scanner(file)) {
            for (Node child : grid.getChildren()) {
                if (child instanceof NumberTextField) {
                    ((NumberTextField) child).setText(
                            String.valueOf(scanner.nextInt()).replace("0", "")
                    );
                }
            }
        } catch (IOException ignore){}
    }

    /**
     * Returns the Texfield corresponding to a row and column in a GridPane gp.
     * @param row row in GridPane
     * @param column column in GridPande
     * @param gp GridPane
     * @return TextField at row, column in gp
     */
    private TextField getTextField(final int row, final int column, GridPane gp) {
        for (Node node : gp.getChildren()) {
            if (GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == column && node instanceof TextField) {
                return (TextField) node;
            }
        }

        return null;
    }
}
