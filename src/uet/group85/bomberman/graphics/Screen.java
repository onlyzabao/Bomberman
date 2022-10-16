package uet.group85.bomberman.graphics;

import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;

public interface Screen {
    void handleEvent(Scene scene);
    void update();
    void render(GraphicsContext gc);
}
