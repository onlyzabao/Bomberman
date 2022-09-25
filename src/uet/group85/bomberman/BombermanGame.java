package uet.group85.bomberman;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class BombermanGame extends Application {
    @Override
    public void start(Stage stage) {
        VBox layout = new VBox();
        Scene scene = new Scene(layout, 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}