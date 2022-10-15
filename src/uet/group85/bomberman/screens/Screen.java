package uet.group85.bomberman.screens;

import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;

public interface Screen {
    void handleEvent(Scene scene);
    void update(double elapsedTime);
    void render(GraphicsContext gc);
}
