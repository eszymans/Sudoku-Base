package komponentowe;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class MainFormController {

    private ResourceBundle bundle;
    private Locale currentLocale;

    @FXML
    private ComboBox<String> languageComboBox;

    @FXML
    private Label chooseDifficulty;

    @FXML
    private Button easyButton;

    @FXML
    private Button mediumButton;

    @FXML
    private Button hardButton;

    @FXML
    public void initialize() {
        // Inicjalizacja języka domyślnego
        currentLocale = new Locale("pl");
        SudokuBoardController.language = "Polski";
        setLanguage(currentLocale);

        // Inicjalizacja językowego ComboBox
        languageComboBox.getItems().addAll("Polski", "English");
        languageComboBox.setValue("Polski");
        languageComboBox.setOnAction(event -> {
            String selectedLanguage = languageComboBox.getValue();
            if (selectedLanguage.equals("Polski")) {
                setLanguage(new Locale("pl"));
            } else if (selectedLanguage.equals("English")) {
                setLanguage(new Locale("en"));
            }
        });
    }

    @FXML
    public void handleEasy(ActionEvent event) {
        SudokuBoardController.difficulty = SudokuBoardLevelDifficulty.EASY;
        loadSudokuBoard(event);
    }

    @FXML
    public void handleMedium(ActionEvent event) {
        SudokuBoardController.difficulty = SudokuBoardLevelDifficulty.MEDIUM;
        loadSudokuBoard(event);
    }

    @FXML
    public void handleHard(ActionEvent event) {
        SudokuBoardController.difficulty = SudokuBoardLevelDifficulty.HARD;
        loadSudokuBoard(event);
    }

    private void setLanguage(Locale locale) {
        // Przechowujemy aktualny język
        currentLocale = locale;
        bundle = ResourceBundle.getBundle("komponentowe.mainForm", locale);

        // Aktualizacja tekstów interfejsu
        chooseDifficulty.setText(bundle.getString("chooseDifficulty"));
        easyButton.setText(bundle.getString("easyButton"));
        mediumButton.setText(bundle.getString("mediumButton"));
        hardButton.setText(bundle.getString("hardButton"));
    }

    private void loadSudokuBoard(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/komponentowe/SudokuBoardView.fxml"));

            // Ustawienie tego samego języka dla nowego widoku
            loader.setResources(ResourceBundle.getBundle("komponentowe.messages", currentLocale));
            Scene scene = new Scene(loader.load());
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
