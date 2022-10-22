package uet.group85.bomberman.graphics;

import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import uet.group85.bomberman.managers.ScreenManager;

import java.util.HashMap;
import java.util.Map;

public class PauseScreen implements Screen {
    // -------------- Buttons ------------------
    private final Map<String, Text> buttons = new HashMap<>(3);
    // --------- Event Handle Auxiliaries --------
    private int pointer;
    private boolean isChosen;

    public PauseScreen() {
        buttons.put("Resume", new Text("Resume"));
        buttons.get("Resume").setY(200);

        buttons.put("Restart", new Text("Restart"));
        buttons.get("Restart").setY(240);

        buttons.put("Exit", new Text("Exit"));
        buttons.get("Exit").setY(280);

        buttons.forEach((String, Text) -> {
            Text.setFont(ScreenManager.gc.getFont());
            Text.setWrappingWidth(ScreenManager.WIDTH);
            Text.setTextAlignment(TextAlignment.CENTER);
            ScreenManager.root.getChildren().add(Text);
        });

        pointer = 0;
        isChosen = false;
    }

    @Override
    public void show() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void handleEvent() {
        ScreenManager.scene.setOnKeyPressed(keyEvent -> {
            switch (keyEvent.getCode()) {
                case ESCAPE -> ScreenManager.switchScreen(ScreenManager.ScreenType.GAME);
                case UP -> pointer = pointer > 0 ? --pointer : 2;
                case DOWN -> pointer = pointer < 2 ? ++pointer : 0;
                case X -> isChosen = true;
            }
        });
    }

    @Override
    public void update() {
        switch (pointer) {
            case 0 -> {
                buttons.get("Resume").setFill(Color.color(0.85, 0.18, 0.06));
                buttons.get("Restart").setFill(Color.WHITE);
                buttons.get("Exit").setFill(Color.WHITE);
            }
            case 1 -> {
                buttons.get("Resume").setFill(Color.WHITE);
                buttons.get("Restart").setFill(Color.color(0.85, 0.18, 0.06));
                buttons.get("Exit").setFill(Color.WHITE);
            }
            case 2 -> {
                buttons.get("Resume").setFill(Color.WHITE);
                buttons.get("Restart").setFill(Color.WHITE);
                buttons.get("Exit").setFill(Color.color(0.85, 0.18, 0.06));
            }
        }
        if (!isChosen) {
            return;
        }
        switch (pointer) {
            case 0 -> ScreenManager.switchScreen(ScreenManager.ScreenType.GAME);
            case 1 -> {
                ScreenManager.bufferScreen.clear();
                ScreenManager.bufferScreen = null;
                ScreenManager.switchScreen(ScreenManager.ScreenType.GAME);
            }
            case 2 -> {
                ScreenManager.bufferScreen.clear();
                ScreenManager.bufferScreen = null;
                ScreenManager.switchScreen(ScreenManager.ScreenType.MENU);
            }
        }
    }

    @Override
    public void render() {
        ScreenManager.gc.setFill(Color.BLACK);
        ScreenManager.gc.fillRect(0, 0, ScreenManager.WIDTH, ScreenManager.HEIGHT);
    }

    @Override
    public void clear() {
        buttons.forEach((String, Text) -> ScreenManager.root.getChildren().remove(Text));
    }
}
