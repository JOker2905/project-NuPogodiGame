package Elements;


import javafx.scene.SubScene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;


public class GameSubScene extends SubScene{

    private final static String BACKGROUND_IMAGE = "/Images/red_panel.png";

    private  boolean isHidden;


    public GameSubScene() {
        super(new AnchorPane(), 600, 400);
        prefWidth(600);
        prefHeight(400);

        BackgroundImage image = new BackgroundImage(new Image(BACKGROUND_IMAGE, 600, 400, false, true),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, null);

        AnchorPane root2 = (AnchorPane) this.getRoot();
        root2.setBackground(new Background(image));

        isHidden = true ;

        setLayoutX(1024);
        setLayoutY(180);

    }

    public void moveSubScene() {

        if (isHidden) {
            setLayoutX(350);
            isHidden = false;
        } else {
            setLayoutX(1024);
            isHidden = true ;
        }
    }

    public AnchorPane getPane() {
        return (AnchorPane) this.getRoot();
    }

}
