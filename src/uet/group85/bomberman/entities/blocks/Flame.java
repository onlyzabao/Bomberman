package uet.group85.bomberman.entities.blocks;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import uet.group85.bomberman.auxiliaries.Coordinate;
import uet.group85.bomberman.auxiliaries.Rectangle;
import uet.group85.bomberman.entities.characters.Character;
import uet.group85.bomberman.entities.characters.Kondoria;
import uet.group85.bomberman.entities.tiles.Grass;
import uet.group85.bomberman.graphics.Sprite;
import uet.group85.bomberman.managers.GameManager;

public class Flame extends Block {
    private final double EXPLOSION_PERIOD = 0.3;
    private final double explodedTime;
    private boolean willCreateEnemy;
    private final Image[] img;

    public Flame(Coordinate mapPos, Coordinate screenPos, Image[] img) {
        super(mapPos, screenPos, new Rectangle(0, 0, Sprite.SCALED_SIZE, Sprite.SCALED_SIZE), true);
        this.img = img;
        explodedTime = GameManager.elapsedTime;
    }


    private void checkCollision() {
        if (GameManager.bomber.isCollided(this)) {
            GameManager.bomber.eliminateNow(GameManager.elapsedTime);
        }

        for (Character enemy : GameManager.enemies) {
            if (enemy.isCollided(this)) {
                enemy.eliminateNow(GameManager.elapsedTime);
            }
        }

        Coordinate unitPos = mapPos.divide(Sprite.SCALED_SIZE);
        Grass belowGrass = (Grass) GameManager.tiles.get(unitPos.y).get(unitPos.x);
        if (belowGrass.getBottomLayer() instanceof Flame) {
            return;
        }
        willCreateEnemy = true;
    }

    private Image getFrame(Image[] frame, double time) {
        double frameDuration = 0.1;
        int index = (int) (((time - explodedTime) % EXPLOSION_PERIOD) / frameDuration);
        return frame[index];
    }

    @Override
    public void update() {
        if (GameManager.elapsedTime - explodedTime > EXPLOSION_PERIOD) {
            if (willCreateEnemy) {
                for (int i = 0; i < 3; i++) {
                    GameManager.enemies.add(new Kondoria(new Coordinate(mapPos), new Coordinate(screenPos)));
                }
            }
            this.isExist = false;
        } else {
            checkCollision();
        }
    }

    @Override
    public void render(GraphicsContext gc) {
        gc.drawImage(getFrame(img, GameManager.elapsedTime), this.screenPos.x, this.screenPos.y);
    }
}
