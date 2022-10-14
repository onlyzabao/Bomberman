package uet.group85.bomberman.screens;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import uet.group85.bomberman.managers.GameManager;
import uet.group85.bomberman.managers.MapManager;
import uet.group85.bomberman.managers.ScreenManager;

import java.io.FileNotFoundException;

public class GameScreen extends Screen {
    private static final int UP = 0;
    private static final int DOWN = 1;
    public static final int LEFT = 2;
    public static final int RIGHT = 3;
    public static final int BOMB = 4;
    public static final int TOTAL = 5;

    public GameScreen(Canvas canvas) {
        super(canvas);
        try {
            MapManager.loadMap(1);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    @Override
    public void handleEvent() {
        scene.setOnKeyPressed(
                keyEvent -> {
                    switch (keyEvent.getCode()) {
                        case UP:
                            if (!GameManager.events[UP]) GameManager.events[UP] = true;
                            break;
                        case DOWN:
                            if (!GameManager.events[DOWN]) GameManager.events[DOWN] = true;
                            break;
                        case LEFT:
                            if (!GameManager.events[LEFT]) GameManager.events[LEFT] = true;
                            break;
                        case RIGHT:
                            if (!GameManager.events[RIGHT]) GameManager.events[RIGHT] = true;
                            break;
                        case X:
                            if (!GameManager.events[BOMB]) GameManager.events[BOMB] = true;
                            break;
                        case A:
                            ScreenManager.switchScreen(ScreenManager.ScreenType.MENU);
                    }
                }
        );
        scene.setOnKeyReleased(
                keyEvent -> {
                    switch (keyEvent.getCode()) {
                        case UP:
                            if (GameManager.events[UP]) GameManager.events[UP] = false;
                            break;
                        case DOWN:
                            if (GameManager.events[DOWN]) GameManager.events[DOWN] = false;
                            break;
                        case LEFT:
                            if (GameManager.events[LEFT]) GameManager.events[LEFT] = false;
                            break;
                        case RIGHT:
                            if (GameManager.events[RIGHT]) GameManager.events[RIGHT] = false;
                            break;
                        case X:
                            if (GameManager.events[BOMB]) GameManager.events[BOMB] = false;
                            break;
                    }
                }
        );
    }

    @Override
    public void update(double elapsedTime) {
        GameManager.update(elapsedTime);
    }

    @Override
    public void render(GraphicsContext gc) {
        GameManager.render(gc);
    }
}
