package Elements;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.text.Font;

public class GameInfoLabel extends Label{

    public final static String FONT_PATH = "/Images/font.ttf";

    public GameInfoLabel(String text) {

        setPrefWidth(380);
        setPrefHeight(60);
        setText(text);
        setWrapText(true);
        setLabelFont();
        setAlignment(Pos.CENTER);
    }

    private void setLabelFont() {

        setFont(Font.loadFont(getClass().getResourceAsStream(FONT_PATH), 23));

    }


}
