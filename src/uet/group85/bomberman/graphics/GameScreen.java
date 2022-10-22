package uet.group85.bomberman.graphics;

import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import uet.group85.bomberman.BombermanGame;
import uet.group85.bomberman.entities.Entity;
import uet.group85.bomberman.managers.GameManager;
import uet.group85.bomberman.managers.ScreenManager;
import uet.group85.bomberman.managers.SoundManager;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class GameScreen implements Screen {
    public final double WAIT_PERIOD = 2.0;
    // Button events
    private static final int UP = 0;
    private static final int DOWN = 1;
    public static final int LEFT = 2;
    public static final int RIGHT = 3;
    public static final int BOMB = 4;
    public static final int TRANSLATED_X = 0;
    public static final int TRANSLATED_Y = 64;
    // Data
    private final Map<String, Integer> data = new HashMap<>(8);
    // Nodes
    private final Map<String, Text> logs = new HashMap<>(3);
    // Time
    private double startedTime;
    private double pausedTime;
    private double endedTime;
    private boolean isEnding;

    public GameScreen() {
        logs.put("Time", new Text());
        logs.put("Score", new Text());
        logs.put("Chance", new Text());

        logs.get("Time").setX(32);
        logs.get("Time").setY(40);
        logs.get("Score").setX(240);
        logs.get("Score").setY(40);
        logs.get("Chance").setX(488);
        logs.get("Chance").setY(40);

        DropShadow ds = new DropShadow();
        ds.setRadius(0.5);
        ds.setOffsetX(2.0);
        ds.setOffsetY(2.0);

        logs.forEach((String, Text) -> {
            Text.setFont(ScreenManager.gc.getFont());
            Text.setFill(Color.WHITE);
            Text.setEffect(ds);
            ScreenManager.root.getChildren().add(Text);
        });

        SoundManager.loadGameSound();
        init();
    }

    public void init() {
        loadData();
        GameManager.clear();
        GameManager.init(data);
        startedTime = BombermanGame.elapsedTime;
        pausedTime = startedTime;
        endedTime = startedTime;
        isEnding = false;
    }

    public void loadData() {
        try {
            BufferedReader rd = new BufferedReader(new FileReader("res/data/history.txt"));
            data.put("Score", Integer.parseInt(rd.readLine()));
            data.put("Level", Integer.parseInt(rd.readLine()));
            data.put("Chance", Integer.parseInt(rd.readLine()));
            data.put("Bomb", Integer.parseInt(rd.readLine()));
            data.put("Flame", Integer.parseInt(rd.readLine()));
            data.put("Speed", Integer.parseInt(rd.readLine()));
            data.put("BombPass", Integer.parseInt(rd.readLine()));
            data.put("WallPass", Integer.parseInt(rd.readLine()));
            rd.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveData() {
        try {
            BufferedWriter wt = new BufferedWriter(new FileWriter("res/data/history.txt"));
            wt.write(String.format("%d\n", data.get("Score")));
            wt.write(String.format("%d\n", data.get("Level")));
            wt.write(String.format("%d\n", data.get("Chance")));
            wt.write(String.format("%d\n", data.get("Bomb")));
            wt.write(String.format("%d\n", data.get("Flame")));
            wt.write(String.format("%d\n", data.get("Speed")));
            wt.write(String.format("%d\n", data.get("BombPass")));
            wt.write(String.format("%d\n", data.get("WallPass")));
            wt.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void hide() {
        logs.forEach((String, Text) -> Text.toBack());
        pausedTime = BombermanGame.elapsedTime;
    }

    @Override
    public void show() {
        logs.forEach((String, Text) -> Text.toFront());
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
        if (BombermanGame.elapsedTime - startedTime > WAIT_PERIOD) {
            if (GameManager.status == GameManager.Status.PLAYING) {
                updatePlayingScreen();
            } else {
                // Switch screen
                if (isEnding) {
                    if (BombermanGame.elapsedTime - endedTime > WAIT_PERIOD) {
                        ScreenManager.switchScreen(ScreenManager.ScreenType.MENU);
                    }
                } else {
                    if (GameManager.status == GameManager.Status.WON) {
                        data.replace("Score", GameManager.score);
                        data.replace("Level", ++GameManager.level);
                        data.replace("Chance", GameManager.chance);
                        data.replace("Bomb", GameManager.bomber.getNumOfBombs());
                        data.replace("Flame", GameManager.bomber.getFlameLen());
                        data.replace("Speed", GameManager.bomber.getBonusSpeed());
                        data.replace("BombPass", GameManager.bomber.getCanPassBomb());
                        data.replace("WallPass", GameManager.bomber.getCanPassBrick());
                    } else {
                        data.replace("Chance", --GameManager.chance);
                    }
                    saveData();
                    if (GameManager.chance > 0) {
                        init();
                    } else {
                        endedTime = BombermanGame.elapsedTime;
                        isEnding = true;
                    }
                }
            }
        }
    }

    private void updatePlayingScreen() {
        // Update time
        GameManager.elapsedTime = BombermanGame.elapsedTime - WAIT_PERIOD - startedTime;
        if (GameManager.elapsedTime > 200) {
            GameManager.status = GameManager.Status.LOST;
            return;
        }
        // Update status board
        logs.get("Time").setText(String.format("Time  %.0f", 200.0 - GameManager.elapsedTime));
        logs.get("Score").setText(String.format("Score  %d", GameManager.score));
        logs.get("Chance").setText(String.format("Left  %d", GameManager.chance));
        // Update playground
        GameManager.bomber.update();
        GameManager.enemies.removeIf(enemy -> !enemy.isExist());
        GameManager.enemies.forEach(Entity::update);
        GameManager.tiles.forEach(tile -> tile.forEach(Entity::update));
    }

    @Override
    public void render() {
        if (GameManager.status == GameManager.Status.WON && isEnding) {
            renderWaitScreen("You won!");
            return;
        }
        if (GameManager.status == GameManager.Status.LOST && isEnding) {
            renderWaitScreen("You lost!");
            return;
        }
        if (BombermanGame.elapsedTime - startedTime < WAIT_PERIOD) {
            renderWaitScreen(String.format("Stage  %d", GameManager.level));
        } else {
            renderPlayingScreen();
        }
    }

    private void renderPlayingScreen() {
        ScreenManager.canvas.toBack();
        // Render playground
        GameManager.tiles.forEach(tile -> tile.forEach(block -> {
            if (block.isVisible()) {
                block.render(ScreenManager.gc);
            }
        }));
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

    private void renderWaitScreen(String message) {
        ScreenManager.canvas.toFront();
        ScreenManager.gc.setFill(Color.BLACK);
        ScreenManager.gc.fillRect(0, 0, ScreenManager.WIDTH, ScreenManager.HEIGHT);
        ScreenManager.gc.setFill(Color.WHITE);
        ScreenManager.gc.fillText(message, 320, 240);
    }

    @Override
    public void clear() {
        logs.forEach((String, Text) -> ScreenManager.root.getChildren().remove(Text));
        logs.clear();
        SoundManager.clearGameSound();
        GameManager.clear();
    }
}