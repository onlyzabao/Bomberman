package uet.group85.bomberman.graphics;


import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import uet.group85.bomberman.managers.ScreenManager;

public class PauseScreen implements Screen {
    public final ScreenManager manager;
    public PauseScreen(ScreenManager manager) {
        this.manager = manager;
    }
    @Override
    public void handleEvent() {
        manager.scene.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ESCAPE) {
                manager.switchScreen(ScreenManager.ScreenType.NEW_GAME);
            }
        });
    }

    @Override
    public void update() {

    }

    @Override
    public void render() {
        manager.gc.setFill(Color.color(0.65, 0.65, 0.65));
        manager.gc.fillRect(0, 0, ScreenManager.WIDTH, ScreenManager.HEIGHT);
    }
}
