package uet.group85.bomberman;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;

import uet.group85.bomberman.auxilities.Coordinate;
import uet.group85.bomberman.auxilities.KeyCode;
import uet.group85.bomberman.entities.Entity;
import uet.group85.bomberman.entities.blocks.Block;
import uet.group85.bomberman.entities.blocks.Grass;
import uet.group85.bomberman.entities.blocks.Wall;
import uet.group85.bomberman.entities.bomb.Bomb;
import uet.group85.bomberman.entities.characters.Bomber;
import uet.group85.bomberman.entities.characters.Character;
import uet.group85.bomberman.entities.items.Item;
import uet.group85.bomberman.graphics.Sprite;
import uet.group85.bomberman.managers.MapManager;

import java.util.ArrayList;
import java.util.List;

public class BombermanGame extends Application {
    // Specifications
    public static final int WIDTH = 25;
    public static final int HEIGHT = 15;

    // Manage game objects - TODO: ObjectManager
    public final List<Block> blocks = new ArrayList<>();
    public final List<Character> enemies = new ArrayList<>();
    public final List<Item> items = new ArrayList<>();
    public final List<Bomb> bombs = new ArrayList<>();
    public final Bomber bomberman = new Bomber(this, new Coordinate(2, 1));
    MapManager mapManager;

    // Manage graphics
    private final Canvas canvas = new Canvas(Sprite.SCALED_SIZE * WIDTH, Sprite.SCALED_SIZE * HEIGHT);
    public final GraphicsContext gc = canvas.getGraphicsContext2D();

    // Manage control input - TODO: ControlManager
    public final boolean[] keyPressed = new boolean[KeyCode.TOTAL];
    // Manage time - TODO: Timer
    public double elapsedTime;

    public static void main(String[] args) {
        Application.launch(BombermanGame.class);
    }

    @Override
    public void start(Stage stage) {
        // Root container
        Group root = new Group();
        root.getChildren().add(canvas);

        // Play ground screen - TODO: ScreenManager
        Scene scene = new Scene(root);

        // Handle key press events
        scene.setOnKeyPressed(
                keyEvent -> {
                    switch (keyEvent.getCode()) {
                        case UP:
                            if (!keyPressed[KeyCode.UP]) keyPressed[KeyCode.UP] = true;
                            break;
                        case DOWN:
                            if (!keyPressed[KeyCode.DOWN]) keyPressed[KeyCode.DOWN] = true;
                            break;
                        case LEFT:
                            if (!keyPressed[KeyCode.LEFT]) keyPressed[KeyCode.LEFT] = true;
                            break;
                        case RIGHT:
                            if (!keyPressed[KeyCode.RIGHT]) keyPressed[KeyCode.RIGHT] = true;
                            break;
                        case X:
                            if (!keyPressed[KeyCode.X]) keyPressed[KeyCode.X] = true;
                            break;
                    }
                }
        );
        scene.setOnKeyReleased(
                keyEvent -> {
                    switch (keyEvent.getCode()) {
                        case UP:
                            if (keyPressed[KeyCode.UP]) keyPressed[KeyCode.UP] = false;
                            break;
                        case DOWN:
                            if (keyPressed[KeyCode.DOWN]) keyPressed[KeyCode.DOWN] = false;
                            break;
                        case LEFT:
                            if (keyPressed[KeyCode.LEFT]) keyPressed[KeyCode.LEFT] = false;
                            break;
                        case RIGHT:
                            if (keyPressed[KeyCode.RIGHT]) keyPressed[KeyCode.RIGHT] = false;
                            break;
                        case X:
                            if (keyPressed[KeyCode.X]) keyPressed[KeyCode.X] = false;
                            break;
                    }
                }
        );

        // Add scene to stage
        stage.setScene(scene);
        stage.show();

        // Manage frames
        final long startNanoTime = System.nanoTime();
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long currentNanoTime) {
                elapsedTime = (currentNanoTime - startNanoTime) / 1000000000.0;
                update();
                render();
            }
        };
        timer.start();

        // Add game components
        createMap();
        bombs.add(new Bomb(this));
        bombs.add(new Bomb(this));
        bombs.add(new Bomb(this));
    }

    public void createMap() {
        // TODO: Read map from a file
        // TODO: Make a map contains wall
        // TODO: Make a map contains brick
            // TODO: Config brick class so it can break, contains items, etc.
        for (int j = 0; j < HEIGHT; j++) {
            for (int i = 0; i < WIDTH; i++) {
                Block object;
                if (j == 0 || j == HEIGHT - 1 || i == 0 || i == WIDTH - 1 || i == 2 && j == 2) {
                    object = new Wall(new Coordinate(i, j));
                }
                else {
                    if (i % 2 == 0 && j % 2 == 0) {
                        object = new Wall(new Coordinate(i, j));
                    } else {
                        object = new Grass(new Coordinate(i, j));
                    }
                }
                blocks.add(object);
            }
        }
        /*
        Temporarily turn of map manager to get more space for testing
         */
        //mapManager = new MapManager(this);
    }

    public void update() {
        // Update each objects and Check for game status
        enemies.forEach(Character::update);
        bombs.forEach(Bomb::update);
        bomberman.update();
    }

    public void render() {
        // Draw objects
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        blocks.forEach(g -> g.render(gc));
        enemies.forEach(g -> g.render(gc));
        bombs.forEach(g -> g.render(gc));
        bomberman.render(gc);
    }
}
