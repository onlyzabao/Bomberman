package uet.group85.bomberman.managers;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import uet.group85.bomberman.BombermanGame;
import uet.group85.bomberman.graphics.GameScreen;
import uet.group85.bomberman.graphics.PauseScreen;
import uet.group85.bomberman.graphics.Screen;

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
    public static Screen screen = new GameScreen(BombermanGame.elapsedTime, 0,1);
    public static Screen bufferScreen;

    public static void init() {
        root.getChildren().add(canvas);
        screen.handleEvent(scene);
    }
    public static void switchScreen(ScreenType type) {
        switch (type) {
            case GAME -> {
                System.out.println("Game");
                screen = bufferScreen;
                double pauseDuration = ((GameScreen) screen).getPausedTime() - ((GameScreen) screen).getStartedTime();
                ((GameScreen) screen).setStartedTime(BombermanGame.elapsedTime - pauseDuration);
            }
            case MENU -> {
                System.out.println("Menu");
            }
            case PAUSE -> {
                System.out.println("Pause");
                ((GameScreen) screen).setPausedTime(BombermanGame.elapsedTime);
                bufferScreen = screen;
                screen = new PauseScreen();
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
