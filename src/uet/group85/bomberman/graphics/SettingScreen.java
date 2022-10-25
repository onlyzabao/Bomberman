package uet.group85.bomberman.graphics;

import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import uet.group85.bomberman.managers.ScreenManager;
import uet.group85.bomberman.managers.SoundManager;

import java.util.HashMap;
import java.util.Map;

public class SettingScreen implements Screen {
    // ------------- Buttons --------------
    private final Map<String, Text> buttons = new HashMap<>(3);
    // ------------- Event Handler Auxiliaries ------------
    private int pointer;
    private boolean isChosen;

    public SettingScreen() {
        buttons.put("Sound", new Text(String.format("Sound  %s", SoundManager.isSoundMuted ? "Off" : "On")));
        buttons.get("Sound").setY(200);

        buttons.put("Music", new Text(String.format("Music  %s", SoundManager.isMusicMuted ? "Off" : "On")));
        buttons.get("Music").setY(240);

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
    public void show() {}

    @Override
    public void hide() {}

    @Override
    public void handleEvent() {
        ScreenManager.scene.setOnKeyPressed(keyEvent -> {
            switch (keyEvent.getCode()) {
                case UP -> {
                    SoundManager.playGameSound("Switch", false);
                    pointer = pointer > 0 ? --pointer : 2;
                }
                case DOWN -> {
                    SoundManager.playGameSound("Switch", false);
                    pointer = pointer < 2 ? ++pointer : 0;
                }
                case X -> isChosen = true;
            }
        });
    }

    @Override
    public void update() {
        switch (pointer) {
            case 0 -> {
                buttons.get("Sound").setFill(Color.color(0.85, 0.18, 0.06));
                buttons.get("Music").setFill(Color.WHITE);
                buttons.get("Exit").setFill(Color.WHITE);
            }
            case 1 -> {
                buttons.get("Sound").setFill(Color.WHITE);
                buttons.get("Music").setFill(Color.color(0.85, 0.18, 0.06));
                buttons.get("Exit").setFill(Color.WHITE);
            }
            case 2 -> {
                buttons.get("Sound").setFill(Color.WHITE);
                buttons.get("Music").setFill(Color.WHITE);
                buttons.get("Exit").setFill(Color.color(0.85, 0.18, 0.06));
            }
        }
        if (!isChosen) {
            return;
        }
        switch (pointer) {
            case 0 -> {
                SoundManager.isSoundMuted = !SoundManager.isSoundMuted;
                buttons.get("Sound").setText(String.format("Sound  %s", SoundManager.isSoundMuted ? "Off" : "On"));
            }
            case 1 -> {
                SoundManager.isMusicMuted = !SoundManager.isMusicMuted;
                if (SoundManager.isMusicMuted) {
                    SoundManager.pauseGameMusic("TM");
                } else {
                    SoundManager.playGameMusic("TM");
                }
                buttons.get("Music").setText(String.format("Music  %s", SoundManager.isMusicMuted ? "Off" : "On"));
            }
            case 2 -> ScreenManager.switchScreen(ScreenManager.ScreenType.MENU);
        }
        isChosen = false;
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
