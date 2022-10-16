package uet.group85.bomberman.graphics;

import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import uet.group85.bomberman.managers.ScreenManager;

public class PauseScreen implements Screen {
    public PauseScreen() {

    }
    @Override
    public void handleEvent(Scene scene) {
        scene.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ESCAPE) {
                ScreenManager.switchScreen(ScreenManager.ScreenType.GAME);
            }
        });
    }

    @Override
    public void update() {

    }

    @Override
    public void render(GraphicsContext gc) {
        gc.setFill(Color.color(0.65, 0.65, 0.65));
        gc.fillRect(0, 0, ScreenManager.WIDTH, ScreenManager.HEIGHT);
    }
}
