package uet.group85.bomberman.graphics;

import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;

public interface Screen {
    void handleEvent();
    void update();
    void render();
}
