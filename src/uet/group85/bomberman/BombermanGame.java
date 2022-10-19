package uet.group85.bomberman;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.stage.Stage;

import uet.group85.bomberman.managers.ScreenManager;

import java.io.FileNotFoundException;


public class BombermanGame extends Application {
    public static double elapsedTime;
    public static void main(String[] args) {
        Application.launch(BombermanGame.class);
    }

    @Override
    public void start(Stage stage) throws FileNotFoundException {
        stage.setTitle("Bomberman");
        ScreenManager.init();
        // Manage frames
        final long startNanoTime = System.nanoTime();
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long currentNanoTime) {
                elapsedTime = (currentNanoTime - startNanoTime) / 1000000000.0;
                ScreenManager.update();
                ScreenManager.render();
            }
        };
        timer.start();

        stage.setScene(ScreenManager.scene);
        stage.show();
    }
}
