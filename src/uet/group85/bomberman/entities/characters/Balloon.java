package uet.group85.bomberman.entities.characters;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import uet.group85.bomberman.auxilities.Coordinate;
import uet.group85.bomberman.auxilities.Rectangle;
import uet.group85.bomberman.entities.tiles.Tile;
import uet.group85.bomberman.graphics.Sprite;
import uet.group85.bomberman.managers.GameManager;

import java.util.List;
import java.util.Random;

public class Balloon extends Character {
    // Specifications
    enum FrameType {
        MOVING, DYING
    }

    public Balloon(Coordinate mapPos, Coordinate screenPos) {
        super(mapPos, screenPos, new Rectangle(2, 2, 28, 28), 2, 3, true);

        dyingFrame = new Image[] {
                Sprite.balloom_dead.getFxImage(),
                Sprite.mob_dead1.getFxImage() ,
                Sprite.mob_dead2.getFxImage(),
                Sprite.mob_dead3.getFxImage()
                // TODO: Get more sprites
        };

        movingFrame = new Image[][] {
                {Sprite.balloom_left1.getFxImage(), Sprite.balloom_left2.getFxImage(), Sprite.balloom_left3.getFxImage()},
                {Sprite.balloom_right1.getFxImage(), Sprite.balloom_right2.getFxImage(), Sprite.balloom_right3.getFxImage()}
        };

        frameDuration = new double[] {0.2, 1.0};
    }

    private void move() {
        Random random = new Random();
        int dir = random.nextInt(4);
        switch (dir) {
            case 0:
                Direction down = Direction.DOWN;
                break;
            case 1:
                Direction UP = Direction.UP;
                break;
            case 2:
                Direction left = Direction.LEFT;
                break;
            case 3:
                Direction right = Direction.RIGHT;
                break;
        }
        if (!isCollided((Tile) GameManager.tiles)) {
            step();
        }
        if(isCollided((List<Tile>) GameManager.bomber)){
            isDying = true ;
        }



        // TODO: Select direction (stepDirection attribute in Character class) randomly
        // TODO: Handle collision with map (use isCollided(GameManager.blocks))
        // TODO: Handle collision with bomber (use isCollided(GameManager.bomberman))
    }

    private void step() {
        switch (stepDirection) {
            case UP -> mapPos.y -= stepLength;
            case DOWN -> mapPos.y += stepLength;
            case LEFT -> mapPos.x -= stepLength;
            case RIGHT -> mapPos.x += stepLength;
        }
    }

    @Override
    public void update() {
        if (isExist) {
            if (isDying) {
                move();
            } else if (GameManager.elapsedTime - deadTime > DYING_PERIOD) {
                GameManager.enemies.remove(this);
            }
        }
    }

    @Override
    public void render(GraphicsContext gc) {
        if (isExist) {
            if (isDying) {
                // TODO: Render base on stepDirection
                gc.drawImage(getFrame(movingFrame[stepDirection.ordinal()], GameManager.elapsedTime,
                        frameDuration[Balloon.FrameType.MOVING.ordinal()]), screenPos.x, screenPos.y);
            } else {
                // TODO: Render dead frame
                gc.drawImage(getFrame(dyingFrame, GameManager.elapsedTime,
                        frameDuration[Balloon.FrameType.DYING.ordinal()]), screenPos.x, screenPos.y);
            }
        }
    }
}
