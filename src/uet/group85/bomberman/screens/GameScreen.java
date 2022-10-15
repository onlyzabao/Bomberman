package uet.group85.bomberman.screens;

import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import uet.group85.bomberman.managers.GameManager;
import uet.group85.bomberman.managers.MapManager;
import uet.group85.bomberman.managers.ScreenManager;

import java.io.FileNotFoundException;

public class GameScreen implements Screen {
    private static final int UP = 0;
    private static final int DOWN = 1;
    public static final int LEFT = 2;
    public static final int RIGHT = 3;
    public static final int BOMB = 4;
    public static final int TOTAL = 5;

    public GameScreen() {
        try {
            MapManager.loadMap(1);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    @Override
    public void handleEvent(Scene scene) {
        scene.setOnKeyPressed(
                keyEvent -> {
                    switch (keyEvent.getCode()) {
                        case UP -> {
                            GameManager.events[UP] = true;
                        }
                        case DOWN -> {
                            GameManager.events[DOWN] = true;
                        }
                        case LEFT -> {
                            GameManager.events[LEFT] = true;
                        }
                        case RIGHT -> {
                            GameManager.events[RIGHT] = true;
                        }
                        case X -> {
                            GameManager.events[BOMB] = true;
                        }
                        case ESCAPE -> {
                            ScreenManager.switchScreen(ScreenManager.ScreenType.PAUSE);
                        }
                    }
                }
        );
        scene.setOnKeyReleased(
                keyEvent -> {
                    switch (keyEvent.getCode()) {
                        case UP -> {
                            GameManager.events[UP] = false;
                        }
                        case DOWN -> {
                            GameManager.events[DOWN] = false;
                        }
                        case LEFT -> {
                            GameManager.events[LEFT] = false;
                        }
                        case RIGHT -> {
                            GameManager.events[RIGHT] = false;
                        }
                        case X -> {
                            GameManager.events[BOMB] = false;
                        }
                    }
                }
        );
    }

    @Override
    public void update(double time) {
        GameManager.update(time);
    }

    @Override
    public void render(GraphicsContext gc) {
        GameManager.render(gc);
    }
}
