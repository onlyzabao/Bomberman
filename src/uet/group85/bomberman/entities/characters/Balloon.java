package uet.group85.bomberman.entities.characters;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import uet.group85.bomberman.BombermanGame;
import uet.group85.bomberman.auxilities.Coordinate;
import uet.group85.bomberman.auxilities.Rectangle;
import uet.group85.bomberman.graphics.Sprite;

public class Balloon extends Character {
    // Specifications
    enum FrameType {
        MOVING, DYING
    }

    public Balloon(BombermanGame engine, Coordinate pos) {
        super(engine, pos, new Rectangle(2, 2, 28, 28), 2, 3);

        dyingFrame = new Image[] {
                Sprite.balloom_dead.getFxImage()
                // TODO: Get more sprites
        };

        movingFrame = new Image[][] {
                {Sprite.balloom_left1.getFxImage(), Sprite.balloom_left2.getFxImage(), Sprite.balloom_left3.getFxImage()},
                {Sprite.balloom_right1.getFxImage(), Sprite.balloom_right2.getFxImage(), Sprite.balloom_right3.getFxImage()}
        };

        frameDuration = new double[] {0.2, 1.0};
    }

    private void move() {
        // TODO: Select direction (stepDirection attribute in Character class) randomly
        // TODO: Handle collision with map (use isCollided(engine.blocks))
        // TODO: Handle collision with bomber (use isCollided(engine.bomberman))
    }

    @Override
    public void update() {
        if (isExist) {
            if (isLiving) {
                move();
            } else if (engine.elapsedTime - deadTime > deadDuration) {
                engine.enemies.remove(this);
            }
        }
    }

    @Override
    public void render(GraphicsContext gc) {
        if (isExist) {
            if (isLiving) {
                // TODO: Render base on stepDirection
            } else {
                // TODO: Render dead frame
            }
        }
    }
}
