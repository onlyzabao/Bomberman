package uet.group85.bomberman.screens;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

public abstract class Screen {
    protected final Group root;
    protected final Scene scene;
    public Screen(Canvas canvas) {
        root = new Group();
        root.getChildren().add(canvas);
        scene = new Scene(root);
    }

    public Group getRoot() {
        return root;
    }

    public Scene getScene() {
        return scene;
    }

    public abstract void handleEvent();
    public abstract void update(double elapsedTime);
    public abstract void render(GraphicsContext gc);
}
