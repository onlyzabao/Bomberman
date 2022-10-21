package uet.group85.bomberman.graphics;

import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import uet.group85.bomberman.managers.ScreenManager;

public class PauseScreen implements Screen {
    private enum ButtonType {
        RESUME, RESTART, EXIT, TOTAL
    }
    private final Text[] buttons = new Text[ButtonType.TOTAL.ordinal()];
    private int pointer;
    private boolean isChosen;
    public PauseScreen() {
        buttons[ButtonType.RESUME.ordinal()] = new Text("Resume");
        buttons[ButtonType.RESTART.ordinal()] = new Text("Restart");
        buttons[ButtonType.EXIT.ordinal()] = new Text("Exit");

        buttons[ButtonType.RESUME.ordinal()].setFont(ScreenManager.gc.getFont());
        buttons[ButtonType.RESTART.ordinal()].setFont(ScreenManager.gc.getFont());
        buttons[ButtonType.EXIT.ordinal()].setFont(ScreenManager.gc.getFont());

        buttons[ButtonType.RESUME.ordinal()].setWrappingWidth(ScreenManager.WIDTH);
        buttons[ButtonType.RESUME.ordinal()].setTextAlignment(TextAlignment.CENTER);
        buttons[ButtonType.RESUME.ordinal()].setY(200);

        buttons[ButtonType.RESTART.ordinal()].setWrappingWidth(ScreenManager.WIDTH);
        buttons[ButtonType.RESTART.ordinal()].setTextAlignment(TextAlignment.CENTER);
        buttons[ButtonType.RESTART.ordinal()].setY(240);

        buttons[ButtonType.EXIT.ordinal()].setWrappingWidth(ScreenManager.WIDTH);
        buttons[ButtonType.EXIT.ordinal()].setTextAlignment(TextAlignment.CENTER);
        buttons[ButtonType.EXIT.ordinal()].setY(280);

        ScreenManager.root.getChildren().add(buttons[ButtonType.RESUME.ordinal()]);
        ScreenManager.root.getChildren().add(buttons[ButtonType.RESTART.ordinal()]);
        ScreenManager.root.getChildren().add(buttons[ButtonType.EXIT.ordinal()]);

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
                case ESCAPE -> {
                    clear();
                    ScreenManager.switchScreen(ScreenManager.ScreenType.GAME);
                }
                case UP -> pointer = pointer > 0 ? --pointer : 2;
                case DOWN -> pointer = pointer < 2 ? ++pointer : 0;
                case X -> isChosen = true;
            }
        });
    }

    @Override
    public void update() {
        int n = ButtonType.TOTAL.ordinal();
        for (int i = 0; i < n; i++) {
            if (pointer == i) {
                buttons[i].setFill(Color.color(0.85, 0.18, 0.06));
            } else {
                buttons[i].setFill(Color.WHITE);
            }
        }
        if (isChosen) {
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
    }

    @Override
    public void render() {
        ScreenManager.gc.setFill(Color.BLACK);
        ScreenManager.gc.fillRect(0, 0, ScreenManager.WIDTH, ScreenManager.HEIGHT);
    }

    @Override
    public void clear() {
        ScreenManager.root.getChildren().remove(buttons[ButtonType.RESUME.ordinal()]);
        ScreenManager.root.getChildren().remove(buttons[ButtonType.RESTART.ordinal()]);
        ScreenManager.root.getChildren().remove(buttons[ButtonType.EXIT.ordinal()]);
    }
}
