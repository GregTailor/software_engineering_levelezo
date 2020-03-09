package hu.unideb.inf;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    private static int difficulty;
    private static String lastGame;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("main_menu.fxml"));
        Parent fxml = fxmlLoader.load();
        scene = new Scene(fxml, 540,760);
        stage.setTitle("Sodoku");
        stage.setScene(scene);
        stage.show();
    }

    static void setRoot(Parent fxml) throws IOException {
        scene.setRoot(fxml);
    }

    static void setDifficulty(int difficulty){
        App.difficulty = difficulty;
    }

    static int getDifficulty(){
        return difficulty;
    }

    public static void setLastGame(String lastGame) {
        App.lastGame = lastGame;
    }

    public static String getLastGame() {
        return lastGame;
    }

    public static Parent loadFXML(String name) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(name + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}