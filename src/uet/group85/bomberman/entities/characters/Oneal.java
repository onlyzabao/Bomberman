package uet.group85.bomberman.entities.characters;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import uet.group85.bomberman.uitilities.AStar;
import uet.group85.bomberman.uitilities.Coordinate;
import uet.group85.bomberman.uitilities.Rectangle;
import uet.group85.bomberman.graphics.Sprite;
import uet.group85.bomberman.managers.GameManager;
import uet.group85.bomberman.managers.SoundManager;

import java.util.*;


public class Oneal extends Character {
    /*
    Faster than Balloon, Oneal move erratically and pursue Bomberman when Bomberman come close.
     */
    public Oneal(Coordinate mapPos, Coordinate screenPos) {
        super(mapPos, screenPos, new Rectangle(8, 8, 16, 16), 2, 4, true);

        defaultFrame = new Image[]{
                Sprite.oneal_dead.getFxImage()
        };
        dyingFrame = new Image[]{
                Sprite.mob_dead1.getFxImage(),
                Sprite.mob_dead2.getFxImage(),
                Sprite.mob_dead3.getFxImage()
        };
        movingFrame = new Image[][]{
                {Sprite.oneal_left1.getFxImage(), Sprite.oneal_left2.getFxImage(), Sprite.oneal_left3.getFxImage()},
                {Sprite.oneal_right1.getFxImage(), Sprite.oneal_right2.getFxImage(), Sprite.oneal_right3.getFxImage()}
        };
        canPassBomb = false;
        canPassBrick = false;
    }

    private void chooseDirection() {
        List<Direction> directionChoices = new ArrayList<>(4);
        for (int i = 0; i < 4; i++) {
            if (passableDirection[i]) {
                directionChoices.add(Direction.values()[i]);
            }
        }
        if (directionChoices.isEmpty()) {
            stepDirection = Direction.NONE;
            return;
        }
        double distToBomber = GameManager.bomber.getMapPos().manhattanDist(this.mapPos);
        if (distToBomber / Sprite.SCALED_SIZE > 5) {
            if (passableDirection[stepDirection.ordinal()] && passableDirection[Direction.NONE.ordinal()]) {
                if (directionChoices.size() < 3) {
                    return;
                }
            }
        } else {
            AStar aStar = new AStar(this, GameManager.bomber);
            Direction direction = aStar.findPath();
            if (direction != Direction.NONE) {
                stepDirection = direction;
                return;
            }
        }
        stepDirection = directionChoices.get(new Random().nextInt(directionChoices.size()));
    }

    private void updateMapPos() {
        if (++stepCounter == stepDuration) {
            this.hitBox.update(mapPos, solidArea);
            if (isCollided(GameManager.bomber)) {
                GameManager.bomber.eliminateNow(GameManager.elapsedTime);
            }
            if (mapPos.x % Sprite.SCALED_SIZE == 0 && mapPos.y % Sprite.SCALED_SIZE == 0) {
                checkDirection();
                chooseDirection();
            }
            step();
            stepCounter = 0;
        }
    }

    private void updateScreenPos() {
        this.screenPos.x = mapPos.x - GameManager.bomber.getMapPos().x + GameManager.bomber.getScreenPos().x;
        this.screenPos.y = mapPos.y - GameManager.bomber.getMapPos().y + GameManager.bomber.getScreenPos().y;
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
        if (!isDying) {
            updateMapPos();
        } else if (GameManager.elapsedTime - deadTime > DYING_PERIOD) {
            GameManager.score += 200;
            isLiving = false;
        }
        updateScreenPos();
    }

    @Override
    public void render(GraphicsContext gc) {
        if (!isDying) {
            gc.drawImage(getFrame(movingFrame[
                            stepDirection.ordinal() < 2 ? stepDirection.ordinal() :
                                    stepDirection.ordinal() < 4 ? stepDirection.ordinal() - 2 : 0],
                    GameManager.elapsedTime, FrameType.MOVING), screenPos.x, screenPos.y);
        } else {
            if (GameManager.elapsedTime - deadTime < frameDuration[FrameType.INJURED.ordinal()]) {
                gc.drawImage(getFrame(defaultFrame, GameManager.elapsedTime, FrameType.INJURED), screenPos.x, screenPos.y);
                SoundManager.playGameSound("Mod_dead", true);
            } else {
                gc.drawImage(getFrame(dyingFrame, GameManager.elapsedTime, FrameType.DYING), screenPos.x, screenPos.y);
            }
        }
    }
}
