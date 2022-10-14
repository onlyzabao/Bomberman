package uet.group85.bomberman.managers;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import uet.group85.bomberman.screens.GameScreen;
import uet.group85.bomberman.screens.MenuScreen;
import uet.group85.bomberman.screens.Screen;

public class ScreenManager {
    public enum ScreenType {
        MENU, GAME
    }
    public static final int WIDTH = 640;
    public static final int HEIGHT = 416;
    public static final Canvas canvas = new Canvas(WIDTH, HEIGHT);
    public static final GraphicsContext gc = canvas.getGraphicsContext2D();
    public static Screen screen;
    public static void switchScreen(ScreenType type) {
        switch (type) {
            case GAME -> {
                screen = new GameScreen(canvas);
                screen.handleEvent();
            }
            case MENU -> {
                screen = new MenuScreen(canvas);
                screen.handleEvent();
            }
        }
    }
}
