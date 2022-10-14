package uet.group85.bomberman.screens;

import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import uet.group85.bomberman.managers.ScreenManager;

public class MenuScreen extends Screen {
    public MenuScreen(Canvas canvas) {
        super(canvas);
    }

    @Override
    public void handleEvent() {
        System.out.println("Menu");
        scene.setOnKeyPressed(keyEvent -> {
            switch (keyEvent.getCode()) {
                case A -> {
                    ScreenManager.switchScreen(ScreenManager.ScreenType.GAME);
                }
            }
        });
    }

    @Override
    public void update(double elapsedTime) {
    }

    @Override
    public void render(GraphicsContext gc) {
        gc.setFill(Color.BLACK);
        gc.fillText("Hello", 32, 32);
    }
}
