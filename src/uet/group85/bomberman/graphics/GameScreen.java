package uet.group85.bomberman.graphics;

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
    public final ScreenManager manager;
    public enum Status {
        START, PAUSED, RESUME, NEXT, RESTART
    }
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
    private final Text chanceText = new Text();
    // Time
    private double startedTime;
    private double pausedTime;

    public GameScreen(ScreenManager manager, double time, int score, int level, int chance, int[] items) {
        this.manager = manager;
        // Create nodes
        timeText.setFont(manager.gc.getFont());
        scoreText.setFont(manager.gc.getFont());
        chanceText.setFont(manager.gc.getFont());

        timeText.setFill(Color.WHITE);
        scoreText.setFill(Color.WHITE);
        chanceText.setFill(Color.WHITE);

        DropShadow ds = new DropShadow();
        ds.setRadius(0.5);
        ds.setOffsetX(2.0);
        ds.setOffsetY(2.0);
        timeText.setEffect(ds);
        scoreText.setEffect(ds);
        chanceText.setEffect(ds);

        timeText.setX(32);
        timeText.setY(40);
        scoreText.setX(240);
        scoreText.setY(40);
        chanceText.setX(488);
        chanceText.setY(40);

        manager.root.getChildren().add(timeText);
        manager.root.getChildren().add(scoreText);
        manager.root.getChildren().add(chanceText);
        // Create playground
        try {
            new MapManager(score, level, chance, items);
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

    public void hide() {
        timeText.toBack();
        scoreText.toBack();
        chanceText.toBack();
    }

    public void show() {
        timeText.toFront();
        scoreText.toFront();
        chanceText.toFront();
    }

    @Override
    public void handleEvent() {
        manager.scene.setOnKeyPressed(
                keyEvent -> {
                    switch (keyEvent.getCode()) {
                        case UP -> GameManager.events[UP] = true;
                        case DOWN -> GameManager.events[DOWN] = true;
                        case LEFT -> GameManager.events[LEFT] = true;
                        case RIGHT -> GameManager.events[RIGHT] = true;
                        case X -> GameManager.events[BOMB] = true;
                        case ESCAPE -> manager.switchScreen(ScreenManager.ScreenType.PAUSE);
                    }
                }
        );
        manager.scene.setOnKeyReleased(
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
                    GameManager.status = GameManager.Status.LOST;
                    return;
                }
                // Update status board
                timeText.setText(String.format("Time  %.0f", 200.0 - GameManager.elapsedTime));
                scoreText.setText(String.format("Score  %d", GameManager.score));
                chanceText.setText(String.format("Left  %d", GameManager.chance));
                // Update playground
                GameManager.bomber.update();
                GameManager.enemies.removeIf(enemy -> !enemy.isExist());
                GameManager.enemies.forEach(Entity::update);
                GameManager.tiles.forEach(Entity::update);
            } else {
                // Remove nodes
                manager.root.getChildren().remove(timeText);
                manager.root.getChildren().remove(scoreText);
                manager.root.getChildren().remove(chanceText);
                // Switch screen
                if (GameManager.status == GameManager.Status.WON) {
                    manager.switchScreen(ScreenManager.ScreenType.NEXT_GAME);
                } else {
                    if (--GameManager.chance == 0) {
                        manager.switchScreen(ScreenManager.ScreenType.MENU);
                    }
                }
            }
        }
    }

    @Override
    public void render() {
        if (BombermanGame.elapsedTime - startedTime < INIT_PERIOD) {
            // Render beginning screen
            manager.gc.setFill(Color.BLACK);
            manager.gc.fillRect(0, 0, ScreenManager.WIDTH, ScreenManager.HEIGHT);
            manager.gc.setFill(Color.WHITE);
            manager.gc.fillText(String.format("Stage  %d", GameManager.level), 240, 240);
        } else {
            // Render playground
            GameManager.tiles.forEach(block -> {
                if (block.isVisible()) {
                    block.render(manager.gc);
                }
            });
            GameManager.enemies.forEach(enemy -> {
                if (enemy.isVisible()) {
                    enemy.render(manager.gc);
                }
            });
            GameManager.bomber.render(manager.gc);
            // Render status board
            manager.gc.setFill(Color.color(0.65, 0.65, 0.65));
            manager.gc.fillRect(0, 0, ScreenManager.WIDTH, TRANSLATED_Y);
        }
    }
}
