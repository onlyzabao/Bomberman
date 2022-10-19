package uet.group85.bomberman.graphics;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import uet.group85.bomberman.managers.GameManager;
import uet.group85.bomberman.managers.ScreenManager;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;

public class MenuScreen implements Screen {
    private enum ButtonType {
        START, CONTINUE, SETTING, TOTAL
    }
    private final int topScore;
    private final ImageView title;
    private final Text[] buttons = new Text[ButtonType.TOTAL.ordinal()];
    private int pointer;
    private boolean isChosen;
    public MenuScreen() {
        topScore = 0;
        try {
            title = new ImageView(new Image(new FileInputStream("res/textures/general.png")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        title.setX(100);
        title.setY(50);
        title.setFitWidth(440);
        title.setFitHeight(240);

        buttons[ButtonType.START.ordinal()] = new Text("Start");
        buttons[ButtonType.CONTINUE.ordinal()] = new Text("Continue");
        buttons[ButtonType.SETTING.ordinal()] = new Text("Setting");

        buttons[ButtonType.START.ordinal()].setFont(ScreenManager.gc.getFont());
        buttons[ButtonType.CONTINUE.ordinal()].setFont(ScreenManager.gc.getFont());
        buttons[ButtonType.SETTING.ordinal()].setFont(ScreenManager.gc.getFont());

        buttons[ButtonType.START.ordinal()].setWrappingWidth(ScreenManager.WIDTH);
        buttons[ButtonType.START.ordinal()].setTextAlignment(TextAlignment.CENTER);
        buttons[ButtonType.START.ordinal()].setY(360);

        buttons[ButtonType.CONTINUE.ordinal()].setWrappingWidth(ScreenManager.WIDTH);
        buttons[ButtonType.CONTINUE.ordinal()].setTextAlignment(TextAlignment.CENTER);
        buttons[ButtonType.CONTINUE.ordinal()].setY(400);

        buttons[ButtonType.SETTING.ordinal()].setWrappingWidth(ScreenManager.WIDTH);
        buttons[ButtonType.SETTING.ordinal()].setTextAlignment(TextAlignment.CENTER);
        buttons[ButtonType.SETTING.ordinal()].setY(440);

        ScreenManager.root.getChildren().add(title);
        ScreenManager.root.getChildren().add(buttons[ButtonType.START.ordinal()]);
        ScreenManager.root.getChildren().add(buttons[ButtonType.CONTINUE.ordinal()]);
        ScreenManager.root.getChildren().add(buttons[ButtonType.SETTING.ordinal()]);

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
            clear();
            switch (pointer) {
                case 0 -> {
                    try {
                        BufferedWriter wt = new BufferedWriter(new FileWriter("res/data/history.txt"));
                        wt.write("0\n");
                        wt.write("1\n");
                        wt.write("3\n");
                        wt.write("1\n");
                        wt.write("1\n");
                        wt.write("0\n");
                        wt.write("0\n");
                        wt.write("0\n");
                        wt.close();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    ScreenManager.switchScreen(ScreenManager.ScreenType.GAME);
                }
                case 1 -> ScreenManager.switchScreen(ScreenManager.ScreenType.GAME);
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
        ScreenManager.root.getChildren().remove(title);
        ScreenManager.root.getChildren().remove(buttons[MenuScreen.ButtonType.START.ordinal()]);
        ScreenManager.root.getChildren().remove(buttons[MenuScreen.ButtonType.CONTINUE.ordinal()]);
        ScreenManager.root.getChildren().remove(buttons[MenuScreen.ButtonType.SETTING.ordinal()]);
    }
}
