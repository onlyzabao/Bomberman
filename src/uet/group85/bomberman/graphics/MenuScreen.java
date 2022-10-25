package uet.group85.bomberman.graphics;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import uet.group85.bomberman.managers.ScreenManager;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class MenuScreen implements Screen {
    // -------- Specifications ------------
    public int topScore;
    // -------- Patterns & Buttons --------
    private final ImageView title;
    private final Map<String, Text> buttons = new HashMap<>(3);
    // --------- Event Handle Auxiliaries --------
    private int pointer;
    private boolean isChosen;

    public MenuScreen() {
        try {
            title = new ImageView(new Image(new FileInputStream("res/textures/general.png")));
            title.setX(100);
            title.setY(40);
            title.setFitWidth(440);
            title.setFitHeight(240);
            ScreenManager.root.getChildren().add(title);

            buttons.put("Start", new Text("Start"));
            buttons.get("Start").setY(330);

            buttons.put("Continue", new Text("Continue"));
            buttons.get("Continue").setY(370);

            buttons.put("Setting", new Text("Setting"));
            buttons.get("Setting").setY(410);

            buttons.forEach((String, Text) -> {
                Text.setFont(ScreenManager.gc.getFont());
                Text.setWrappingWidth(ScreenManager.WIDTH);
                Text.setTextAlignment(TextAlignment.CENTER);
                ScreenManager.root.getChildren().add(Text);
            });
            ScreenManager.gc.setTextAlign(TextAlignment.CENTER);

            BufferedReader rd1 = new BufferedReader(new FileReader("res/data/record.txt"));
            int oldTopScore = Integer.parseInt(rd1.readLine());
            rd1.close();

            BufferedReader rd2 = new BufferedReader(new FileReader("res/data/history.txt"));
            int newTopScore = Integer.parseInt(rd2.readLine());
            rd2.close();

            if (oldTopScore >= newTopScore) {
                topScore = oldTopScore;
            } else {
                BufferedWriter wt = new BufferedWriter(new FileWriter("res/data/record.txt"));
                wt.write(String.format("%d\n", newTopScore));
                wt.close();
                topScore = newTopScore;
            }
            pointer = 0;
            isChosen = false;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
        switch (pointer) {
            case 0 -> {
                buttons.get("Start").setFill(Color.color(0.85, 0.18, 0.06));
                buttons.get("Continue").setFill(Color.WHITE);
                buttons.get("Setting").setFill(Color.WHITE);
            }
            case 1 -> {
                buttons.get("Start").setFill(Color.WHITE);
                buttons.get("Continue").setFill(Color.color(0.85, 0.18, 0.06));
                buttons.get("Setting").setFill(Color.WHITE);
            }
            case 2 -> {
                buttons.get("Start").setFill(Color.WHITE);
                buttons.get("Continue").setFill(Color.WHITE);
                buttons.get("Setting").setFill(Color.color(0.85, 0.18, 0.06));
            }
        }
        if (!isChosen) {
            return;
        }
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
                    wt.write("0\n");
                    wt.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                ScreenManager.switchScreen(ScreenManager.ScreenType.GAME);
            }
            case 1 -> ScreenManager.switchScreen(ScreenManager.ScreenType.GAME);
            case 2 -> ScreenManager.switchScreen(ScreenManager.ScreenType.SETTING);
        }
    }

    @Override
    public void render() {
        ScreenManager.gc.setFill(Color.BLACK);
        ScreenManager.gc.fillRect(0, 0, ScreenManager.WIDTH, ScreenManager.HEIGHT);
        ScreenManager.gc.setFill(Color.WHITE);
        ScreenManager.gc.fillText(String.format("Top\t%d", topScore), 320, 450);
    }

    @Override
    public void clear() {
        ScreenManager.root.getChildren().remove(title);
        buttons.forEach((String, Text) -> ScreenManager.root.getChildren().remove(Text));
    }
}
