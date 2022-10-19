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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ScreenManager {
    public enum ScreenType {
        NEW_GAME, NEXT_GAME, MENU, PAUSE
    }
    public static final int WIDTH = 640;
    public static final int HEIGHT = 480;
    public final Canvas canvas = new Canvas(WIDTH, HEIGHT);
    public final GraphicsContext gc = canvas.getGraphicsContext2D();
    public final Group root = new Group();
    public final Scene scene = new Scene(root);
    public Screen screen;
    public Screen bufferScreen;

    public ScreenManager() {
        init();
    }

    public void init() {
        try {
            Scanner sc = new Scanner(new File("res/data/history.txt"));
            int score = sc.nextInt();
            int level = sc.nextInt();
            int chance = sc.nextInt();
            sc.nextLine();
            int[] items = new int[3];
            for (int item : items) {
                item = sc.nextInt();
            }
            sc.close();

            gc.setFont(Font.loadFont(new FileInputStream("res/fonts/RetroGaming.ttf"), 32));

            root.getChildren().add(canvas);

            screen = new GameScreen(this, BombermanGame.elapsedTime, 0,1, 3, items);
            screen.handleEvent();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
    public void switchScreen(ScreenType type) {
        switch (type) {
            case NEW_GAME -> {
                screen = bufferScreen;
                double pauseDuration = ((GameScreen) screen).getPausedTime() - ((GameScreen) screen).getStartedTime();
                ((GameScreen) screen).setStartedTime(BombermanGame.elapsedTime - pauseDuration);
                ((GameScreen) screen).show();
            }
            case NEXT_GAME -> {
                //screen = new GameScreen(BombermanGame.elapsedTime, GameManager.score, ++GameManager.level, 3);
            }
            case PAUSE -> {
                bufferScreen = screen;
                ((GameScreen) screen).setPausedTime(BombermanGame.elapsedTime);
                ((GameScreen) screen).hide();
                screen = new PauseScreen(this);
            }
            case MENU -> {
                System.out.println("Menu");
            }
        }
        screen.handleEvent();
    }

    public void update() {
        screen.update();
    }

    public void render() {
        gc.clearRect(0, 0, WIDTH, HEIGHT);
        screen.render();
    }
}
