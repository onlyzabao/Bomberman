package uet.group85.bomberman.entities.characters;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import uet.group85.bomberman.entities.tiles.Tile;
import uet.group85.bomberman.uitilities.Coordinate;
import uet.group85.bomberman.uitilities.Rectangle;
import uet.group85.bomberman.graphics.Sprite;
import uet.group85.bomberman.managers.GameManager;
import uet.group85.bomberman.managers.SoundManager;

import java.util.*;


public class Kondoria extends Character {
    /*
    The slowest enemy in the game, it will pursue Bomberman and can travel through bombs & soft blocks.
     */
    public Kondoria(Coordinate mapPos, Coordinate screenPos) {
        super(mapPos, screenPos, new Rectangle(8, 8, 16, 16), 2, 5, true);

        defaultFrame = new Image[]{
                Sprite.kondoria_dead.getFxImage()
        };
        dyingFrame = new Image[]{
                Sprite.mob_dead1.getFxImage(),
                Sprite.mob_dead2.getFxImage(),
                Sprite.mob_dead3.getFxImage()
        };
        movingFrame = new Image[][]{
                {Sprite.kondoria_left1.getFxImage(), Sprite.kondoria_left2.getFxImage(), Sprite.kondoria_left3.getFxImage()},
                {Sprite.kondoria_right1.getFxImage(), Sprite.kondoria_right2.getFxImage(), Sprite.kondoria_right3.getFxImage()}
        };
        canPassBomb = false;
        canPassBrick = true;
    }

    private void chooseDirection(Tile[] tiles) {
        int minDist = Integer.MAX_VALUE;
        for (int i = 0; i < 4; i++) {
            if (passableDirection[i]) {
                minDist = Math.min(minDist, GameManager.bomber.getMapPos().manhattanDist(tiles[i].getMapPos()));
            }
        }
        List<Direction> directionChoices = new ArrayList<>(5);
        for (int i = 0; i < 4; i++) {
            if (passableDirection[i]) {
                if (GameManager.bomber.getMapPos().manhattanDist(tiles[i].getMapPos()) == minDist) {
                    directionChoices.add(Direction.values()[i]);
                }
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
                Tile[] tiles = checkDirection();
                chooseDirection(tiles);
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
            GameManager.score += 500;
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
