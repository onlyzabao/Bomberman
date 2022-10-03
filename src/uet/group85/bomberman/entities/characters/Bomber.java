package uet.group85.bomberman.entities.characters;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.group85.bomberman.BombermanGame;
import uet.group85.bomberman.auxilities.Coordinate;
import uet.group85.bomberman.auxilities.KeyCode;
import uet.group85.bomberman.auxilities.Rectangle;
import uet.group85.bomberman.entities.blocks.Block;
import uet.group85.bomberman.entities.bomb.Bomb;
import uet.group85.bomberman.graphics.Sprite;

public class Bomber extends Character {
    private final BombermanGame engine;
    // Specifications
    private boolean isMoving;
    private boolean isCoolingDown;
    private double startTime;
    private final double coolDownTime;

    // Constructor
    public Bomber(BombermanGame engine, Coordinate pos) {
        super(pos, new Rectangle(0, 0, 24, 32), 2, 3);

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
        isCoolingDown = false;
        coolDownTime = 0.25;
    }

    private boolean isCollideBlock() {
        this.hitBox.update(this);

        // Detect obstacle
        switch (stepDirection) {
            case UP -> {
                hitBox.topY -= stepLength;
                obstacle1 = engine.blocks.get(BombermanGame.WIDTH * (hitBox.topY / Sprite.SCALED_SIZE) // Row size multiply Row index
                        + (hitBox.leftX / Sprite.SCALED_SIZE)); // add Column index -> One dimension index
                obstacle2 = engine.blocks.get(BombermanGame.WIDTH * (hitBox.topY / Sprite.SCALED_SIZE)
                        + (hitBox.rightX / Sprite.SCALED_SIZE));
            }
            case DOWN -> {
                hitBox.bottomY += stepLength;
                obstacle1 = engine.blocks.get(BombermanGame.WIDTH * (hitBox.bottomY / Sprite.SCALED_SIZE)
                        + (hitBox.leftX / Sprite.SCALED_SIZE));
                obstacle2 = engine.blocks.get(BombermanGame.WIDTH * (hitBox.bottomY / Sprite.SCALED_SIZE)
                        + (hitBox.rightX / Sprite.SCALED_SIZE));
            }
            case LEFT -> {
                hitBox.leftX -= stepLength;
                obstacle1 = engine.blocks.get(BombermanGame.WIDTH * (hitBox.topY / Sprite.SCALED_SIZE)
                        + (hitBox.leftX / Sprite.SCALED_SIZE));
                obstacle2 = engine.blocks.get(BombermanGame.WIDTH * (hitBox.bottomY / Sprite.SCALED_SIZE)
                        + (hitBox.leftX / Sprite.SCALED_SIZE));
            }
            case RIGHT -> {
                hitBox.rightX += stepLength;
                obstacle1 = engine.blocks.get(BombermanGame.WIDTH * (hitBox.topY / Sprite.SCALED_SIZE)
                        + (hitBox.rightX / Sprite.SCALED_SIZE));
                obstacle2 = engine.blocks.get(BombermanGame.WIDTH * (hitBox.bottomY / Sprite.SCALED_SIZE)
                        + (hitBox.rightX / Sprite.SCALED_SIZE));
            }
        }
        assert obstacle1 != null;
        assert obstacle2 != null;
        return isCollided(obstacle1) && (!obstacle1.isPassable())
                || isCollided(obstacle2) && (!obstacle2.isPassable());
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
                    } else {
                        if (obstacle1.isPassable() ^ obstacle2.isPassable()) {
                            autoStep();
                        }
                    }
                }
            }
            stepCounter = 0;
        }
    }

    private void step() {
        switch (stepDirection) {
            case UP -> pos.y -= stepLength;
            case DOWN -> pos.y += stepLength;
            case LEFT -> pos.x -= stepLength;
            case RIGHT -> pos.x += stepLength;
        }
    }

    private void autoStep() {
        if (stepDirection == Direction.UP || stepDirection == Direction.DOWN) {
            if ((hitBox.leftX + 12) / Sprite.SCALED_SIZE == obstacle1.getPos().x / Sprite.SCALED_SIZE) {
                pos.x += (obstacle1.isPassable() ? -1 : 0) * stepLength / 2;
            } else {
                pos.x += (obstacle2.isPassable() ? 1 : 0) * stepLength / 2;
            }
        } else {
            if ((hitBox.topY + 16) / Sprite.SCALED_SIZE == obstacle1.getPos().y / Sprite.SCALED_SIZE) {
                pos.y += (obstacle1.isPassable() ? -1 : 0) * stepLength / 2;
            } else {
                pos.y += (obstacle2.isPassable() ? 1 : 0) * stepLength / 2;
            }
        }
    }

    private void bomb() {
        if (engine.keyPressed[KeyCode.X]) {
            if (isCoolingDown) {
                if (engine.elapsedTime - startTime > coolDownTime) {
                    isCoolingDown = false;
                }
            } else{
                for (Bomb instance : engine.bombs) {
                    if (!instance.isBombed()) {
                        instance.setBombed(true);
                        isCoolingDown = true;
                        startTime = engine.elapsedTime;
                        break;
                    }
                }
            }
        }
    }

    @Override
    public void update() {
        move();
        bomb();
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
