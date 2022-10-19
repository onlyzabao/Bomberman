package uet.group85.bomberman.graphics;


import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import uet.group85.bomberman.managers.ScreenManager;

import java.io.FileNotFoundException;

public class PauseScreen implements Screen {
    public PauseScreen() {

    }

    @Override
    public void show() {

    }

    @Override
    public void hide() {

    }



    @Override
    public void handleEvent() {
        ScreenManager.scene.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ESCAPE) {
                ScreenManager.switchScreen(ScreenManager.ScreenType.GAME);
            }
        });
    }

    @Override
    public void update() {

    }

    @Override
    public void render() {
        ScreenManager.gc.setFill(Color.color(0.65, 0.65, 0.65));
        ScreenManager.gc.fillRect(0, 0, ScreenManager.WIDTH, ScreenManager.HEIGHT);
    }
}
