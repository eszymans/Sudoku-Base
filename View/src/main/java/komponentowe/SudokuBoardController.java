package komponentowe;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import javafx.scene.control.TextFormatter;
import komponentowe.exception.DaoException;
import komponentowe.exception.DataAccessException;
import komponentowe.exception.JdbcDaoException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

public class SudokuBoardController {

    private static final Logger logger = LoggerFactory.getLogger(SudokuBoardController.class);
    private static final String DB_URL = "jdbc:sqlserver://localhost:1433;database=TestDB;encrypt=true;trustServerCertificate=true;";
    private static final String DB_USER = "sa";
    private static final String DB_PASSWORD = "Password_123";

    public static SudokuBoardLevelDifficulty difficulty;
    private SudokuBoard board;
    private SudokuBoard originalBoard;
    private ResourceBundle bundle;
    private ResourceBundle authorsBundle;
    public static String language;
    private final SudokuBoardDaoFactory factory = new SudokuBoardDaoFactory();
    private JdbcSudokuBoardDao jdbcDao = (JdbcSudokuBoardDao) factory.getJdbcDao(DB_URL, DB_USER, DB_PASSWORD);

    @FXML
    private Button saveButton;

    @FXML
    private Button loadButton;

    @FXML
    private ComboBox<String> loadComboBox;

    @FXML
    private GridPane sudokuGrid;

    @FXML
    private ComboBox<String> languageComboBox;

    @FXML
    private Label languageLabel;

    @FXML
    private Label authorsLabel;

    public SudokuBoardController() throws SQLException {
    }

    @FXML
    public void initialize() throws JdbcDaoException, DaoException {
        authorsBundle = ResourceBundle.getBundle("komponentowe.AuthorsResourceBundle");
        setAuthors();
        setLoadComboBox();
        setupLanguage();

        try {
            board = new SudokuBoard(new BacktrackingSudokuSolver());
            board.solveGame();
            SudokuBoard boardToSolve = difficulty.sudokuBoardCellsRemover(board);
            board = boardToSolve;
            originalBoard = (SudokuBoard) boardToSolve.clone();
            displayBoard();
        } catch (CloneNotSupportedException e) {
            logger.error("Clone not supported", e);
        }
    }

    private void setupLanguage() {
        languageComboBox.getItems().addAll("Polski", "English");
        languageComboBox.setValue(language);
        languageComboBox.setOnAction(event -> {
            String selectedLanguage = languageComboBox.getValue();
            language = selectedLanguage;
            if (selectedLanguage.equals("Polski")) {
                setLanguage(new Locale("pl"), event);
            } else if (selectedLanguage.equals("English")) {
                setLanguage(new Locale("en"), event);
            }
        });
    }
    private void setLanguage(Locale locale, javafx.event.ActionEvent event) {
        try {
            bundle = ResourceBundle.getBundle("komponentowe.messages", locale);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/komponentowe/SudokuBoardView.fxml"));
            loader.setResources(bundle);

            Scene scene = new Scene(loader.load());
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showErrorDialog("Failed to load the view. Please try again.");
        }
    }

    private void showErrorDialog(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void setLoadComboBox() throws JdbcDaoException {
        List<String> boardNames = jdbcDao.names();

        String regex = "Original_.*";

        loadComboBox.getItems().clear();

        for (String name : boardNames) {
            String originalName = "Original_" + name;

            if (boardNames.contains(originalName) && !name.matches(regex)) {
                loadComboBox.getItems().add(name);
            }
        }
    }

    private void setAuthors() {
        String author1 = authorsBundle.getString("author1");
        String author2 = authorsBundle.getString("author2");

        authorsLabel.setText(author1 + ", " + author2);
    }

    private void displayBoard() {
        sudokuGrid.getChildren().clear();

        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                TextField cell = new TextField();
                cell.setPrefSize(50, 50);
                int value = board.get(row, col);

                setupTextField(cell, row, col, value);

                sudokuGrid.add(cell, col, row);
                GridPane.setFillHeight(cell, true);
                GridPane.setFillWidth(cell, true);
            }
        }
    }

    private void setupTextField(TextField cell, int row, int col, int value) {
        // Konwerter: 0 <-> ""
        StringConverter<Number> converter = new StringConverter<>() {
            @Override
            public String toString(Number value) {
                return value == null || value.intValue() == 0 ? "" : String.valueOf(value);
            }

            @Override
            public Number fromString(String string) {
                return string == null || string.isEmpty() ? 0 : Integer.parseInt(string);
            }
        };

        // Wyrażenie regularne dla walidacji
        Pattern validInputPattern = Pattern.compile("[1-9]?");

        UnaryOperator<TextFormatter.Change> filter = change -> {
            String newText = change.getControlNewText();
            if (validInputPattern.matcher(newText).matches()) {
                return change;
            }
            return null;
        };

        TextFormatter<Number> formatter = new TextFormatter<>(converter, value, filter);

        cell.setTextFormatter(formatter);

        formatter.valueProperty().addListener((observable, oldValue, newValue) -> {
            board.set(row, col, newValue.intValue());
        });

        cell.setEditable(value == 0);
    }

    @FXML
    private void handleSave() throws JdbcDaoException {
        TextInputDialog dialog = new TextInputDialog("MojaPlansza");
        dialog.setTitle("Zapisz planszę");
        dialog.setHeaderText("Podaj nazwę dla planszy:");
        dialog.setContentText("Nazwa:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(fileName -> {
            try {
                jdbcDao.write(fileName, board);
                jdbcDao.write("Original_" + fileName, originalBoard);
                logger.info("Zapisano planszę jako: {}", fileName);
            } catch (IOException | JdbcDaoException e) {
                logger.error("Błąd podczas zapisu planszy: {}", e.getMessage());
            }
            try {
                setLoadComboBox();
            } catch (JdbcDaoException e) {
                logger.error("Błąd przy odświeżaniu listy zapisanych plansz: {}", e.getMessage());
            }
        });
    }

    @FXML
    private void handleLoad() throws DataAccessException, DaoException {
        String boardName = loadComboBox.getValue();
        if (boardName != null) {
            try {
                board = jdbcDao.read("Original_" + boardName);
                displayBoard();
                updateBoard(jdbcDao.read(boardName));
                logger.info("Wczytano planszę: {}", boardName);
            } catch (IOException | SQLException e) {
                logger.error("Błąd podczas wczytywania planszy: {}", e.getMessage());
            }
        }
    }
    private void updateBoard(SudokuBoard boardToLoad) {
        for (Node node : sudokuGrid.getChildren()) {
            if (node instanceof TextField) {
                int col = GridPane.getColumnIndex(node);
                int row = GridPane.getRowIndex(node);

                int value = board.get(row, col);
                int newValue = boardToLoad.get(row, col);
                TextField cell = (TextField) node;

                if (value != newValue) {
                    cell.setText(String.valueOf(newValue));
                    cell.setEditable(true);
                }
            }
        }
    }
}
