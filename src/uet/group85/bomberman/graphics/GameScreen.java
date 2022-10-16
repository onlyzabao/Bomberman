package uet.group85.bomberman.graphics;

import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import uet.group85.bomberman.BombermanGame;
import uet.group85.bomberman.entities.Entity;
import uet.group85.bomberman.managers.GameManager;
import uet.group85.bomberman.managers.MapManager;
import uet.group85.bomberman.managers.ScreenManager;

import java.io.FileNotFoundException;

public class GameScreen implements Screen {
    public final double INIT_PERIOD = 2.0;
    // Button events
    private static final int UP = 0;
    private static final int DOWN = 1;
    public static final int LEFT = 2;
    public static final int RIGHT = 3;
    public static final int BOMB = 4;
    public static final int TRANSLATED_X = 0;
    public static final int TRANSLATED_Y = 64;
    // Nodes
    private final Text timeText = new Text();
    private final Text scoreText = new Text();
    private final Text levelText = new Text();
    // Time
    private double startedTime;
    private double pausedTime;

    public GameScreen(double time, int score, int level) {
        // Create nodes
        timeText.setFont(ScreenManager.gc.getFont());
        scoreText.setFont(ScreenManager.gc.getFont());
        levelText.setFont(ScreenManager.gc.getFont());

        timeText.setFill(Color.WHITE);
        scoreText.setFill(Color.WHITE);
        levelText.setFill(Color.WHITE);

        DropShadow ds = new DropShadow();
        ds.setRadius(0.5);
        ds.setOffsetX(2.0);
        ds.setOffsetY(2.0);
        timeText.setEffect(ds);
        scoreText.setEffect(ds);
        levelText.setEffect(ds);

        timeText.setX(32);
        timeText.setY(40);
        scoreText.setX(224);
        scoreText.setY(40);
        levelText.setX(480);
        levelText.setY(40);

        ScreenManager.root.getChildren().add(timeText);
        ScreenManager.root.getChildren().add(scoreText);
        ScreenManager.root.getChildren().add(levelText);
        // Create playground
        try {
            new MapManager(score, level);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        startedTime = time;
        pausedTime = startedTime;
    }


    public void setStartedTime(double startedTime) {
        this.startedTime = startedTime;
    }

    public double getStartedTime() {
        return startedTime;
    }

    public void setPausedTime(double pausedTime) {
        this.pausedTime = pausedTime;
    }

    public double getPausedTime() {
        return pausedTime;
    }

    @Override
    public void handleEvent(Scene scene) {
        scene.setOnKeyPressed(
                keyEvent -> {
                    switch (keyEvent.getCode()) {
                        case UP -> GameManager.events[UP] = true;
                        case DOWN -> GameManager.events[DOWN] = true;
                        case LEFT -> GameManager.events[LEFT] = true;
                        case RIGHT -> GameManager.events[RIGHT] = true;
                        case X -> GameManager.events[BOMB] = true;
                        case ESCAPE -> ScreenManager.switchScreen(ScreenManager.ScreenType.PAUSE);
                    }
                }
        );
        scene.setOnKeyReleased(
                keyEvent -> {
                    switch (keyEvent.getCode()) {
                        case UP -> GameManager.events[UP] = false;
                        case DOWN -> GameManager.events[DOWN] = false;
                        case LEFT -> GameManager.events[LEFT] = false;
                        case RIGHT -> GameManager.events[RIGHT] = false;
                        case X -> GameManager.events[BOMB] = false;
                    }
                }
        );
    }

    @Override
    public void update() {
        if (BombermanGame.elapsedTime - startedTime > INIT_PERIOD) {
            if (GameManager.status == GameManager.Status.PLAYING) {
                // Update time
                GameManager.elapsedTime = BombermanGame.elapsedTime - INIT_PERIOD - startedTime;
                if (GameManager.elapsedTime > 200) {
                    GameManager.status = GameManager.Status.LOSE;
                    return;
                }
                // Update status board
                timeText.setText(String.format("Time  %.0f", 200.0 - GameManager.elapsedTime));
                scoreText.setText(String.format("Score  %d", GameManager.score));
                levelText.setText(String.format("Stage  %d", GameManager.level));
                // Update playground
                GameManager.bomber.update();
                GameManager.enemies.removeIf(enemy -> !enemy.isExist());
                GameManager.enemies.forEach(Entity::update);
                GameManager.tiles.forEach(Entity::update);
            } else {
                // Remove nodes
                ScreenManager.root.getChildren().remove(timeText);
                ScreenManager.root.getChildren().remove(scoreText);
                ScreenManager.root.getChildren().remove(levelText);
                // Switch screen
                if (GameManager.status == GameManager.Status.WIN) {
                    ScreenManager.switchScreen(ScreenManager.ScreenType.NEXT_GAME);
                } else {
                    ScreenManager.switchScreen(ScreenManager.ScreenType.MENU);
                }
            }
        }
    }

    @Override
    public void render(GraphicsContext gc) {
        if (BombermanGame.elapsedTime - startedTime < INIT_PERIOD) {
            // Render beginning screen
            gc.setFill(Color.BLACK);
            gc.fillRect(0, 0, ScreenManager.WIDTH, ScreenManager.HEIGHT);
            gc.setFill(Color.WHITE);
            gc.fillText(String.format("Stage  %d", GameManager.level), 240, 240);
        } else {
            // Render playground
            GameManager.tiles.forEach(block -> {
                if (block.isVisible()) {
                    block.render(gc);
                }
            });
            GameManager.enemies.forEach(enemy -> {
                if (enemy.isVisible()) {
                    enemy.render(gc);
                }
            });
            GameManager.bomber.render(gc);
            // Render status board
            gc.setFill(Color.color(0.65, 0.65, 0.65));
            gc.fillRect(0, 0, ScreenManager.WIDTH, TRANSLATED_Y);
        }
    }
}
