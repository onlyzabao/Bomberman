package uet.group85.bomberman;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;

import uet.group85.bomberman.managers.ScreenManager;
import uet.group85.bomberman.screens.GameScreen;


public class BombermanGame extends Application {
    // Manage graphics
    public double elapsedTime;

    public static void main(String[] args) {
        Application.launch(BombermanGame.class);
    }

    @Override
    public void start(Stage stage) {
        ScreenManager.screen = new GameScreen(ScreenManager.canvas);
        ScreenManager.screen.handleEvent();

        // Manage frames
        final long startNanoTime = System.nanoTime();
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long currentNanoTime) {
                elapsedTime = (currentNanoTime - startNanoTime) / 1000000000.0;
                update();
                render();
            }
        };
        timer.start();

        stage.setScene(ScreenManager.screen.getScene());
        stage.show();
    }

    public void update() {
        // Update each objects and Check for game status
        ScreenManager.screen.update(elapsedTime);
    }

    public void render() {
        // Draw objects
        ScreenManager.gc.clearRect(0, 0, ScreenManager.canvas.getWidth(), ScreenManager.canvas.getHeight());
        ScreenManager.screen.render(ScreenManager.gc);
    }
}
