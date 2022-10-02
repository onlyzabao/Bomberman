package uet.group85.bomberman.entities.characters;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.group85.bomberman.BombermanGame;
import uet.group85.bomberman.auxilities.Bound;
import uet.group85.bomberman.auxilities.Coordinate;
import uet.group85.bomberman.auxilities.KeyCode;
import uet.group85.bomberman.auxilities.Rectangle;
import uet.group85.bomberman.entities.Entity;
import uet.group85.bomberman.entities.blocks.Brick;
import uet.group85.bomberman.entities.blocks.Wall;
import uet.group85.bomberman.entities.items.Item;
import uet.group85.bomberman.graphics.Sprite;

import java.util.List;

public class Bomber extends Character {
    BombermanGame engine;
    boolean isMoving;

    // Constructor
    public Bomber(BombermanGame engine, Coordinate pos) {
        super(pos, new Rectangle(
                0,
                0,
                24,
                32
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

        movingFrame = new Image[][]{
                {Sprite.player_up_1.getFxImage(), Sprite.player_up_2.getFxImage()},
                {Sprite.player_down_1.getFxImage(), Sprite.player_down_2.getFxImage()},
                {Sprite.player_left_1.getFxImage(), Sprite.player_left_2.getFxImage()},
                {Sprite.player_right_1.getFxImage(), Sprite.player_right_2.getFxImage()}
        };

        isMoving = false;
    }

    private boolean isCollideBlock() {
        Bound bomberBound = Bound.createBound(this);
        Entity obstacle1 = null;
        Entity obstacle2 = null;
        switch (stepDirection) {
            case UP -> {
                bomberBound.topY -= stepLength;
                obstacle1 = engine.blocks.get(BombermanGame.WIDTH * (bomberBound.topY / Sprite.SCALED_SIZE) // Row size multiply Row index
                        + (bomberBound.leftX / Sprite.SCALED_SIZE)); // add Column index -> One dimension index
                obstacle2 = engine.blocks.get(BombermanGame.WIDTH * (bomberBound.topY / Sprite.SCALED_SIZE)
                        + (bomberBound.rightX / Sprite.SCALED_SIZE));
            }
            case DOWN -> {
                bomberBound.bottomY += stepLength;
                obstacle1 = engine.blocks.get(BombermanGame.WIDTH * (bomberBound.bottomY / Sprite.SCALED_SIZE)
                        + (bomberBound.leftX / Sprite.SCALED_SIZE));
                obstacle2 = engine.blocks.get(BombermanGame.WIDTH * (bomberBound.bottomY / Sprite.SCALED_SIZE)
                        + (bomberBound.rightX / Sprite.SCALED_SIZE));
            }
            case LEFT -> {
                bomberBound.leftX -= stepLength;
                obstacle1 = engine.blocks.get(BombermanGame.WIDTH * (bomberBound.topY / Sprite.SCALED_SIZE)
                        + (bomberBound.leftX / Sprite.SCALED_SIZE));
                obstacle2 = engine.blocks.get(BombermanGame.WIDTH * (bomberBound.bottomY / Sprite.SCALED_SIZE)
                        + (bomberBound.leftX / Sprite.SCALED_SIZE));
            }
            case RIGHT -> {
                bomberBound.rightX += stepLength;
                obstacle1 = engine.blocks.get(BombermanGame.WIDTH * (bomberBound.topY / Sprite.SCALED_SIZE)
                        + (bomberBound.rightX / Sprite.SCALED_SIZE));
                obstacle2 = engine.blocks.get(BombermanGame.WIDTH * (bomberBound.bottomY / Sprite.SCALED_SIZE)
                        + (bomberBound.rightX / Sprite.SCALED_SIZE));
            }
        }
        assert obstacle1 != null;
        assert obstacle2 != null;
        return isCollided(bomberBound, Bound.createBound(obstacle1))
                && (obstacle1 instanceof Wall || obstacle1 instanceof Brick) // Collide with a non-passable block (1)
                || isCollided(bomberBound, Bound.createBound(obstacle2))
                && (obstacle2 instanceof Wall || obstacle2 instanceof Brick); // Collide with a non-passable block (2)
    }

    private void move() {
        if (++stepCounter == stepDuration) {
            isMoving = false;
            for (int i = 0; i < Direction.TOTAL.ordinal(); i++) {
                if (engine.keyPressed[i]) {
                    stepDirection = Direction.values()[i];
                    isMoving = true;
                    if (!isCollideBlock()) {
                        step();
                    }
                }
            }
            stepCounter = 0;
        }
    }

    private void step() {
        switch (stepDirection) {
            case UP -> {
                pos.y -= stepLength;
            }
            case DOWN -> {
                pos.y += stepLength;
            }
            case LEFT -> {
                pos.x -= stepLength;
            }
            case RIGHT -> {
                pos.x += stepLength;
            }
        }
    }

    @Override
    public void update() {
        move();
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
