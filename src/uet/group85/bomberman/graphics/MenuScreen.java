package uet.group85.bomberman.graphics;

import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import uet.group85.bomberman.managers.ScreenManager;

public class MenuScreen implements Screen {
    public ScreenManager manager;
    public MenuScreen(ScreenManager manager) {
        this.manager = manager;
    }

    @Override
    public void handleEvent() {

    }

    @Override
    public void update() {
    }

    @Override
    public void render() {
        manager.gc.setFill(Color.BLACK);
        manager.gc.fillText("Hello", 32, 32);
    }
}
