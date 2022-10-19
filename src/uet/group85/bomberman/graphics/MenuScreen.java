package uet.group85.bomberman.graphics;

import javafx.scene.paint.Color;
import uet.group85.bomberman.managers.ScreenManager;

import java.io.FileNotFoundException;

public class MenuScreen implements Screen {
    public MenuScreen() {
    }

    @Override
    public void show() {

    }

    @Override
    public void hide() {

    }


    @Override
    public void handleEvent() {

    }

    @Override
    public void update() {
    }

    @Override
    public void render() {
        ScreenManager.gc.setFill(Color.BLACK);
        ScreenManager.gc.fillText("Hello", 32, 32);
    }
}
