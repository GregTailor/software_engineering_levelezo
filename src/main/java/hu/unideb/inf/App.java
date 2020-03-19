package hu.unideb.inf;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    private static SudokuBoard sudokuBoard;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("main_menu.fxml"));
        Parent fxml = fxmlLoader.load();
        scene = new Scene(fxml, 540,760);
        stage.setTitle("Sudoku");
        stage.setScene(scene);
        stage.show();
        File latestFile = new File("latest.json");
        if (latestFile.exists()) {
            App.sudokuBoard = new SudokuBoard(latestFile);
        }
    }

    static void setRoot(Parent fxml) {
        scene.setRoot(fxml);
    }

    public static Parent loadFXML(String name) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(name + ".fxml"));
        return fxmlLoader.load();
    }

    public static SudokuBoard getSudokuBoard() {
        return sudokuBoard;
    }

    public static void setSudokuBoard(SudokuBoard newSudokuBoard){
        sudokuBoard = newSudokuBoard;
    }

    public static void main(String[] args) {
        launch();
    }

}