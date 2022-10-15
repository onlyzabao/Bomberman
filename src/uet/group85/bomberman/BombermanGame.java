package uet.group85.bomberman;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;

import uet.group85.bomberman.managers.ScreenManager;
import uet.group85.bomberman.screens.GameScreen;


public class BombermanGame extends Application {
    public static void main(String[] args) {
        Application.launch(BombermanGame.class);
    }

    @Override
    public void start(Stage stage) {
        ScreenManager.init();

        // Manage frames
        final long startNanoTime = System.nanoTime();
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long currentNanoTime) {
                ScreenManager.update((currentNanoTime - startNanoTime) / 1000000000.0);
                ScreenManager.render();
            }
        };
        timer.start();

        stage.setScene(ScreenManager.scene);
        stage.show();
    }
}
