package uet.group85.bomberman.entities.characters;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.group85.bomberman.BombermanGame;
import uet.group85.bomberman.auxilities.Coordinate;
import uet.group85.bomberman.auxilities.KeyCode;
import uet.group85.bomberman.auxilities.Rectangle;
import uet.group85.bomberman.entities.Entity;
import uet.group85.bomberman.entities.items.Item;
import uet.group85.bomberman.graphics.Sprite;

import java.util.List;

public class Bomber extends Character {
    BombermanGame engine;
    boolean isMoving;
    boolean isCollided;

    // Constructor
    public Bomber(BombermanGame engine, Coordinate pos) {
        super(pos, new Rectangle(
                pos.x + Sprite.SCALED_SIZE / 16,
                pos.y + Sprite.SCALED_SIZE / 16,
                (Sprite.SCALED_SIZE * 7) / 8,
                (Sprite.SCALED_SIZE * 7) / 8
        ), 2, 3);

        this.engine = engine;

        defaultFrame = new Image[]{
                Sprite.player_up.getFxImage(),
                Sprite.player_down.getFxImage(),
                Sprite.player_left.getFxImage(),
                Sprite.player_right.getFxImage()
        };

        dyingFrame = new Image[]{
                Sprite.player_dead1.getFxImage(),
                Sprite.player_dead2.getFxImage(),
                Sprite.player_dead3.getFxImage()
        };

        movingFrame = new Image[][] {
                { Sprite.player_up_1.getFxImage(), Sprite.player_up_2.getFxImage() },
                { Sprite.player_down_1.getFxImage(), Sprite.player_down_2.getFxImage() },
                { Sprite.player_left_1.getFxImage(), Sprite.player_left_2.getFxImage() },
                { Sprite.player_right_1.getFxImage(), Sprite.player_right_2.getFxImage() }
        };

        isMoving = false;
        isCollided = false;
    }

    private void checkCollision(List<Entity> blocks, List<Character> enemies, List<Item> items) {

    }

    private void move(boolean[] keyPressed) {
        if (++stepCounter == stepDuration) {
            if (keyPressed[KeyCode.UP]) {
                stepDirection = Direction.UP;
                isMoving = true;
            } else if (keyPressed[KeyCode.DOWN]) {
                stepDirection = Direction.DOWN;
                isMoving = true;
            } else if (keyPressed[KeyCode.LEFT]) {
                stepDirection = Direction.LEFT;
                isMoving = true;
            } else if (keyPressed[KeyCode.RIGHT]) {
                stepDirection = Direction.RIGHT;
                isMoving = true;
            } else {
                isMoving = false;
            }
            int x = (keyPressed[KeyCode.RIGHT] ? 1 : 0) - (keyPressed[KeyCode.LEFT] ? 1 : 0);
            int y = (keyPressed[KeyCode.DOWN] ? 1 : 0) - (keyPressed[KeyCode.UP] ? 1 : 0);
            pos.add(new Coordinate(x, y).multiply(stepLength));
            stepCounter = 0;
        }
    }

    @Override
    public void update() {
        checkCollision(engine.blocks, engine.enemies, engine.items);
        move(engine.keyPressed);
    }

    @Override
    public void render(GraphicsContext gc) {
        if (isMoving) {
            gc.drawImage(getFrame(movingFrame[stepDirection.ordinal()], engine.elapsedTime), pos.x, pos.y);
        } else {
            gc.drawImage(defaultFrame[stepDirection.ordinal()], pos.x, pos.y);
        }
    }
}
