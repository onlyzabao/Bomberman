package uet.group85.bomberman.graphics;

import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import uet.group85.bomberman.BombermanGame;
import uet.group85.bomberman.entities.Entity;
import uet.group85.bomberman.managers.GameManager;
import uet.group85.bomberman.managers.MapManager;
import uet.group85.bomberman.managers.ScreenManager;

import java.io.*;

public class GameScreen implements Screen {
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
    // Data
    private final int[] data = new int[GameManager.Data.TOTAL.ordinal()];
    // Nodes
    private final Text timeText = new Text();
    private final Text scoreText = new Text();
    private final Text chanceText = new Text();
    // Time
    private double startedTime;
    private double pausedTime;

    public GameScreen(double time) {
        timeText.setFont(ScreenManager.gc.getFont());
        scoreText.setFont(ScreenManager.gc.getFont());
        chanceText.setFont(ScreenManager.gc.getFont());

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

        ScreenManager.root.getChildren().add(timeText);
        ScreenManager.root.getChildren().add(scoreText);
        ScreenManager.root.getChildren().add(chanceText);

        loadData();
        startedTime = time;
        pausedTime = startedTime;
    }

    public void loadData() {
        try {
            BufferedReader rd = new BufferedReader(new FileReader("res/data/history.txt"));
            int n = GameManager.Data.TOTAL.ordinal();
            for (int i = 0; i < n; i++) {
                data[i] = Integer.parseInt(rd.readLine());
            }
            rd.close();
            new MapManager(data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveData() {
        try {
            BufferedWriter wt = new BufferedWriter(new FileWriter("res/data/history.txt"));
            int n = GameManager.Data.TOTAL.ordinal();
            for (int i = 0; i < n; i++) {
                wt.write(String.format("%d\n", data[i]));
            }
            wt.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void hide() {
        timeText.toBack();
        scoreText.toBack();
        chanceText.toBack();
        pausedTime = BombermanGame.elapsedTime;
    }

    @Override
    public void show() {
        timeText.toFront();
        scoreText.toFront();
        chanceText.toFront();
        startedTime = BombermanGame.elapsedTime - (pausedTime - startedTime);
    }

    @Override
    public void handleEvent() {
        ScreenManager.scene.setOnKeyPressed(
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
        ScreenManager.scene.setOnKeyReleased(
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
                // Switch screen
                if (GameManager.status == GameManager.Status.WON) {
                    data[GameManager.Data.SCORE.ordinal()] = GameManager.score;
                    data[GameManager.Data.LEVEL.ordinal()] = ++GameManager.level;
                    data[GameManager.Data.CHANCE.ordinal()] = GameManager.chance;
                    data[GameManager.Data.BONUS_BOMBS.ordinal()] = GameManager.bomber.getNumOfBombs();
                    data[GameManager.Data.FLAME_LEN.ordinal()] = GameManager.bomber.getFlameLen();
                    data[GameManager.Data.SPEED.ordinal()] = GameManager.bomber.getBonusSpeed();
                    data[GameManager.Data.BOMB_PASS.ordinal()] = GameManager.bomber.getCanPassBomb();
                    data[GameManager.Data.WALL_PASS.ordinal()] = GameManager.bomber.getCanPassBrick();
                } else {
                    data[GameManager.Data.CHANCE.ordinal()] = --GameManager.chance;;
                }
                saveData();
                if (GameManager.chance > 0) {
                    loadData();
                    startedTime = BombermanGame.elapsedTime;
                    pausedTime = startedTime;
                } else {
                    ScreenManager.switchScreen(ScreenManager.ScreenType.MENU);
                }
            }
        }
    }

    @Override
    public void render() {
        if (BombermanGame.elapsedTime - startedTime < INIT_PERIOD) {
            ScreenManager.canvas.toFront();
            // Render beginning screen
            ScreenManager.gc.setFill(Color.BLACK);
            ScreenManager.gc.fillRect(0, 0, ScreenManager.WIDTH, ScreenManager.HEIGHT);
            ScreenManager.gc.setFill(Color.WHITE);
            ScreenManager.gc.fillText(String.format("Stage  %d", GameManager.level), 320, 240);
        } else {
            ScreenManager.canvas.toBack();
            // Render playground
            GameManager.tiles.forEach(block -> {
                if (block.isVisible()) {
                    block.render(ScreenManager.gc);
                }
            });
            GameManager.enemies.forEach(enemy -> {
                if (enemy.isVisible()) {
                    enemy.render(ScreenManager.gc);
                }
            });
            GameManager.bomber.render(ScreenManager.gc);
            // Render status board
            ScreenManager.gc.setFill(Color.color(0.65, 0.65, 0.65));
            ScreenManager.gc.fillRect(0, 0, ScreenManager.WIDTH, TRANSLATED_Y);
        }
    }

    @Override
    public void clear() {
        ScreenManager.root.getChildren().remove(timeText);
        ScreenManager.root.getChildren().remove(scoreText);
        ScreenManager.root.getChildren().remove(chanceText);
    }
}
