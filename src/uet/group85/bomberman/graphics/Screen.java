package uet.group85.bomberman.graphics;

import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;

public interface Screen {
    //Font basicFont = Font.loadFont(new FileInputStream("res/fonts/RetroGaming.ttf"));
    void handleEvent(Scene scene);
    void update();
    void render(GraphicsContext gc);
}
