package uet.group85.bomberman.managers;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.text.Font;
import uet.group85.bomberman.BombermanGame;
import uet.group85.bomberman.graphics.GameScreen;
import uet.group85.bomberman.graphics.PauseScreen;
import uet.group85.bomberman.graphics.Screen;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class ScreenManager {
    public enum ScreenType {
        NEW_GAME, NEXT_GAME, MENU, PAUSE
    }
    public static final int WIDTH = 640;
    public static final int HEIGHT = 480;
    public static final Canvas canvas = new Canvas(WIDTH, HEIGHT);
    public static final GraphicsContext gc = canvas.getGraphicsContext2D();
    public static final Group root = new Group();
    public static final Scene scene = new Scene(root);
    public static Screen screen;
    public static Screen bufferScreen;

    public static void init() {
        try {
            gc.setFont(Font.loadFont(new FileInputStream("res/fonts/RetroGaming.ttf"), 32));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        root.getChildren().add(canvas);

        screen = new GameScreen(BombermanGame.elapsedTime, 0,1);
        screen.handleEvent(scene);
    }
    public static void switchScreen(ScreenType type) {
        switch (type) {
            case NEW_GAME -> {
                if (bufferScreen != null) {
                    screen = bufferScreen;
                    double pauseDuration = ((GameScreen) screen).getPausedTime() - ((GameScreen) screen).getStartedTime();
                    ((GameScreen) screen).setStartedTime(BombermanGame.elapsedTime - pauseDuration);
                } else {
                    screen = new GameScreen(BombermanGame.elapsedTime, 0, 1);
                }
            }
            case NEXT_GAME -> {
                screen = new GameScreen(BombermanGame.elapsedTime, GameManager.score, ++GameManager.level);
            }
            case PAUSE -> {
                bufferScreen = screen;
                ((GameScreen) screen).setPausedTime(BombermanGame.elapsedTime);
                screen = new PauseScreen();
            }
            case MENU -> {
                System.out.println("Menu");
            }
        }
        screen.handleEvent(scene);
    }

    public static void update() {
        screen.update();
    }

    public static void render() {
        gc.clearRect(0, 0, WIDTH, HEIGHT);
        screen.render(gc);
    }
}
