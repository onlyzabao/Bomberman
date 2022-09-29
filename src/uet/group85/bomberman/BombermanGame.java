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
import uet.group85.bomberman.entities.blocks.Grass;
import uet.group85.bomberman.entities.blocks.Wall;
import uet.group85.bomberman.entities.bomb.Bomb;
import uet.group85.bomberman.entities.characters.Bomber;
import uet.group85.bomberman.entities.characters.Character;
import uet.group85.bomberman.entities.items.Item;
import uet.group85.bomberman.graphics.Sprite;

import java.util.ArrayList;
import java.util.List;

public class BombermanGame extends Application {
    // Window size
    public static final int WIDTH = 25;
    public static final int HEIGHT = 15;

    // Game components
    private final List<Entity> blocks = new ArrayList<>();
    private final List<Character> enemies = new ArrayList<>();
    private final List<Item> items = new ArrayList<>();
    private final List<Bomb> bombs = new ArrayList<>();
    private Bomber bomberman;

    // Graphic components
    private Canvas canvas;
    private GraphicsContext gc;

    // Manage key press event
    boolean[] keyPressed = new boolean[KeyCode.TOTAL];

    public static void main(String[] args) {
        Application.launch(BombermanGame.class);
    }

    @Override
    public void start(Stage stage) {
        // Create canvas and graphics context
        canvas = new Canvas(Sprite.SCALED_SIZE * WIDTH, Sprite.SCALED_SIZE * HEIGHT);
        gc = canvas.getGraphicsContext2D();

        // Create root container
        Group root = new Group();
        root.getChildren().add(canvas);

        // Create scene
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
                    }
                }
        );

        // Add scene to stage
        stage.setScene(scene);
        stage.show();

        // Manage frames
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                render();
                update();
            }
        };
        timer.start();

        // Add game components
        createMap();

        bomberman = new Bomber(new Coordinate(2, 2), Sprite.player_right.getFxImage(), 1,
                new Coordinate(0, 0), Character.State.ALIVE);
    }

    public void createMap() {
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                Entity object;
                if (j == 0 || j == HEIGHT - 1 || i == 0 || i == WIDTH - 1 || i == 10 && j ==10) {
                    object = new Wall(new Coordinate(i, j), Sprite.wall.getFxImage());
                }
                else {
                    object = new Grass(new Coordinate(i, j), Sprite.grass.getFxImage());
                }
                blocks.add(object);
            }
        }
    }

    public void update() {
        enemies.forEach(Entity::update);
        bomberman.move(keyPressed);
    }

    public void render() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        blocks.forEach(g -> g.render(gc));
        enemies.forEach(g -> g.render(gc));
        bomberman.render(gc);
    }
}
