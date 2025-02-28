package komponentowe;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ResourceBundle;

public class MainFormApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/komponentowe/MainForm.fxml"));
        loader.setResources(ResourceBundle.getBundle("komponentowe.mainForm_pl"));
            VBox root = loader.load();
            Scene scene = new Scene(root);

            stage.setTitle("Sudoku");
            stage.setScene(scene);
            stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
