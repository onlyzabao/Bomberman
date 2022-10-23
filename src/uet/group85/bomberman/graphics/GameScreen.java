package uet.group85.bomberman.graphics;

import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import uet.group85.bomberman.BombermanGame;
import uet.group85.bomberman.managers.GameManager;
import uet.group85.bomberman.managers.ScreenManager;
import uet.group85.bomberman.managers.SoundManager;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class GameScreen implements Screen {
    // ------------- Specifications --------------
    public final double WAIT_PERIOD = 2.0;
    // ------------- Key Event -------------------
    private static final int UP = 0;
    private static final int DOWN = 1;
    public static final int LEFT = 2;
    public static final int RIGHT = 3;
    public static final int BOMB = 4;
    // ------------- Playground Position -----------
    public static final int TRANSLATED_X = 0;
    public static final int TRANSLATED_Y = 64;
    // ------------- Game data ---------------------
    private final Map<String, Integer> data = new HashMap<>(8);
    // ------------- Logs --------------------------
    private final Map<String, Text> logs = new HashMap<>(3);
    // ------------- Timeline ----------------------
    private double startedTime;
    private double pausedTime;
    private double endedTime;
    private boolean isEnding;

    public GameScreen() {
        // Time left log
        logs.put("Time", new Text());
        logs.get("Time").setX(32);
        logs.get("Time").setY(40);
        // Score log
        logs.put("Score", new Text());
        logs.get("Score").setX(240);
        logs.get("Score").setY(40);
        // Player's chances log
        logs.put("Chance", new Text());
        logs.get("Chance").setX(488);
        logs.get("Chance").setY(40);
        // Set font & effect
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
        // Load game music & sounds
        SoundManager.loadGameSound();
        // Start game
        start();
    }

    /**
     * Start game.
     */
    public void start() {
        try {
            loadData();
            GameManager.clear();
            GameManager.init(data);
            startedTime = BombermanGame.elapsedTime;
            pausedTime = startedTime;
            endedTime = startedTime;
            isEnding = false;
        } catch (FileNotFoundException e) {
            GameManager.status = GameManager.Status.WON;
            endedTime = BombermanGame.elapsedTime;
            isEnding = true;
        }
    }

    /**
     * Load game data from previous play.
     */
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

    /**
     * Save game data to disk.
     */
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

    /**
     * Hide game screen.
     */
    @Override
    public void hide() {
        logs.forEach((String, Text) -> Text.toBack());
        pausedTime = BombermanGame.elapsedTime;
        SoundManager.pauseGameMusic("BGM");
    }

    /**
     * Show game screen.
     */
    @Override
    public void show() {
        logs.forEach((String, Text) -> Text.toFront());
        startedTime = BombermanGame.elapsedTime - (pausedTime - startedTime);
        SoundManager.playGameMusic("BGM");
    }

    /**
     * Set event handler.
     */
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
        // Do not update at wait screen
        if (BombermanGame.elapsedTime - startedTime < WAIT_PERIOD) {
            return;
        }
        // Update logs and game objects if the game is on going
        if (GameManager.status == GameManager.Status.PLAYING) {
            GameManager.elapsedTime = BombermanGame.elapsedTime - WAIT_PERIOD - startedTime;
            if (GameManager.elapsedTime > 200) {
                GameManager.status = GameManager.Status.LOST;
                return;
            }
            // Update logs
            logs.get("Time").setText(String.format("Time  %.0f", 200.0 - GameManager.elapsedTime));
            logs.get("Score").setText(String.format("Score  %d", GameManager.score));
            logs.get("Chance").setText(String.format("Left  %d", GameManager.chance));
            // Update game
            GameManager.update();
            SoundManager.playGameMusic("BGM");
            return;
        }
        // Switch screen
        SoundManager.stopGameMusic("BGM");
        if (isEnding) {
            if (BombermanGame.elapsedTime - endedTime > WAIT_PERIOD) {
                ScreenManager.switchScreen(ScreenManager.ScreenType.MENU);
            }
            return;
        }
        if (GameManager.status == GameManager.Status.WON) {
            data.replace("Score", GameManager.score);
            data.replace("Level", ++GameManager.level);
            data.replace("Chance", GameManager.chance);
            data.replace("Bomb", GameManager.bomber.getNumOfBombs());
            data.replace("Flame", GameManager.bomber.getFlameLen());
            data.replace("Speed", GameManager.bomber.getBonusSpeed());
            data.replace("BombPass", GameManager.bomber.getCanPassBomb());
            data.replace("WallPass", GameManager.bomber.getCanPassBrick());
            saveData();
            start();
            return;
        }
        if (GameManager.status == GameManager.Status.LOST) {
            if (--GameManager.chance > 0) {
                data.replace("Chance", GameManager.chance);
                saveData();
                start();
            } else {
                data.replace("Score", 0);
                data.replace("Level", 1);
                data.replace("Chance", 3);
                data.replace("Bomb", 1);
                data.replace("Flame", 1);
                data.replace("Speed", 0);
                data.replace("BombPass", 0);
                data.replace("WallPass", 0);
                saveData();
                endedTime = BombermanGame.elapsedTime;
                isEnding = true;
            }
        }
    }

    @Override
    public void render() {
        if (GameManager.status == GameManager.Status.WON && isEnding) {
            renderWaitScreen("You won!");
            SoundManager.playGameSound("Won", true);
            return;
        }
        if (GameManager.status == GameManager.Status.LOST && isEnding) {
            renderWaitScreen("You lost!");
            SoundManager.playGameSound("Lost", true);
            return;
        }
        if (BombermanGame.elapsedTime - startedTime < WAIT_PERIOD) {
            renderWaitScreen(String.format("Stage  %d", GameManager.level));
            SoundManager.playGameSound("Stage_start", true);
        } else {
            renderPlayScreen();
        }
    }

    private void renderPlayScreen() {
        ScreenManager.canvas.toBack();
        // Render playground
        GameManager.render();
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
        // --------- Temp ------------
        SoundManager.clearGameSound();
        // ---------------------------
        GameManager.clear();
    }
}