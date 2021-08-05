package Elements;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.text.Font;

public class GameSmallInfoLabel extends Label{

    private final static String FONT_PATH = "/Images/font.ttf";

    public GameSmallInfoLabel(String text) {

        setPrefWidth(300);
        setPrefHeight(50);
        setAlignment(Pos.CENTER_LEFT);
        setPadding(new Insets(10, 10, 10, 10));
        setLabelFont();
        setText(text);
    }

    private void setLabelFont() {
        setFont(Font.loadFont(getClass().getResourceAsStream(FONT_PATH), 70));
    }

}
