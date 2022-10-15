package uet.group85.bomberman.screens;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import uet.group85.bomberman.managers.ScreenManager;

public class MenuScreen implements Screen {
    public MenuScreen(Canvas canvas) {

    }

    @Override
    public void handleEvent(Scene scene) {

    }

    @Override
    public void update(double elapsedTime) {
    }

    @Override
    public void render(GraphicsContext gc) {
        gc.setFill(Color.BLACK);
        gc.fillText("Hello", 32, 32);
    }
}
