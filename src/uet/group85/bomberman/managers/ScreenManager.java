package uet.group85.bomberman.managers;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.text.Font;
import uet.group85.bomberman.BombermanGame;
import uet.group85.bomberman.graphics.GameScreen;
import uet.group85.bomberman.graphics.MenuScreen;
import uet.group85.bomberman.graphics.PauseScreen;
import uet.group85.bomberman.graphics.Screen;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class ScreenManager {
    public enum ScreenType {
        GAME, MENU, PAUSE
    }
    public static final int WIDTH = 640;
    public static final int HEIGHT = 480;
    public static final Canvas canvas = new Canvas(WIDTH, HEIGHT);
    public static final GraphicsContext gc = canvas.getGraphicsContext2D();
    public static final Group root = new Group();
    public static final Scene scene = new Scene(root);
    public static Screen screen;
    public static Screen bufferScreen;

    public static void init() throws FileNotFoundException {
        gc.setFont(Font.loadFont(new FileInputStream("res/fonts/RetroGaming.ttf"), 32));
        root.getChildren().add(canvas);
        screen = new MenuScreen();
        screen.handleEvent();
    }

    public static void switchScreen(ScreenType type) {
        switch (type) {
            case GAME -> {
                screen.clear();
                if (bufferScreen != null) {
                    screen = bufferScreen;
                } else {
                    screen = new GameScreen(BombermanGame.elapsedTime);
                }
                screen.show();
            }
            case PAUSE -> {
                screen.hide();
                bufferScreen = screen;
                screen = new PauseScreen();
                screen.show();
            }
            case MENU -> {
                screen.clear();
                screen = new MenuScreen();
                screen.show();
            }
        }
        screen.handleEvent();
    }

    public static void update() {
        screen.update();
    }

    public static void render() {
        gc.clearRect(0, 0, WIDTH, HEIGHT);
        screen.render();
    }
}
