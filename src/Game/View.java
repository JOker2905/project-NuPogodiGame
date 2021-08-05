package Game;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import Elements.GameButton;
import Elements.GameInfoLabel;
import Elements.GameSubScene;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class View {
    private static final int HEIGHT = 768;
    private static final int WIDTH = 1024;
    private AnchorPane mainPane;
    private Scene mainScene;
    private Stage mainStage;

    private final static int MENU_BUTTON_START_X = 100;
    private final static int MENU_BUTTON_START_Y = 150;

    private GameSubScene creditsSubscene;
    private GameSubScene helpSubscene;
    private GameSubScene scoreSubscene;

    private GameSubScene sceneToHide;
   private static boolean isscorebuttonclicked=false;

    List<GameButton> menuButtons;
    ListView<String>listView;
    ObservableList<String>observableList= FXCollections.observableArrayList();

    public View(){
        menuButtons = new ArrayList<>();
        mainPane = new AnchorPane();
        mainScene = new Scene(mainPane, WIDTH, HEIGHT );
        mainStage = new Stage();
        mainStage.setScene(mainScene);
        createSubScenes();
        CreateButtons();
        createBackground();
        createLogo();
        mainStage.setResizable(false);
    }
    private void showSubScene(GameSubScene subScene) {
        if (sceneToHide != null) {
            sceneToHide.moveSubScene();
        }

        subScene.moveSubScene();
        sceneToHide = subScene;
    }
    private void createSubScenes() {

        creditsSubscene = new GameSubScene();
        mainPane.getChildren().add(creditsSubscene);
        helpSubscene = new GameSubScene();
        mainPane.getChildren().add(helpSubscene);
        scoreSubscene = new GameSubScene();
        mainPane.getChildren().add(scoreSubscene);


        createCreditsSubScene();
        createHelpSubScene();
        createScoreSubScene();
    }
    private void createScoreSubScene(){
        scoreSubscene=new GameSubScene();
        mainPane.getChildren().add(scoreSubscene);
        listView=new ListView<>();
        listView.setLayoutX(30);
        listView.setLayoutY(25);
        listView.setPrefSize(530,350);
        scoreSubscene.getPane().getChildren().add(listView);

    }
    private void createCreditsSubScene(){
        creditsSubscene=new GameSubScene();
        mainPane.getChildren().add(creditsSubscene);

        GameInfoLabel creditsLabel=new GameInfoLabel("created by \nMaciek Siekierko");
        creditsLabel.setLayoutX(110);
        creditsLabel.setLayoutY(25);
        creditsSubscene.getPane().getChildren().add(creditsLabel);
    }
    private void createHelpSubScene(){
        helpSubscene=new GameSubScene();
        mainPane.getChildren().add(helpSubscene);
        GameInfoLabel help=new GameInfoLabel("HELP");
        help.setLayoutX(150);
        help.setLayoutY(20);
        GridPane helpgrid=new GridPane();
        helpgrid.setLayoutX(100);
        helpgrid.setLayoutY(100);
        Label wolfmove=new Label("Move wolf by arrow keys.");
        Label egghelp=new Label("You need to catch egg before it drop");
        Label lifehelp=new Label("You lose game when you drop 3 eggs");
        helpgrid.add(wolfmove,1,0);
        helpgrid.add(egghelp,1,1);
        helpgrid.add(lifehelp,1,2);
        helpSubscene.getPane().getChildren().addAll(help,helpgrid);
    }

    public Stage getMainStage() {
        return mainStage;
    }

    private void AddMenuButtons(GameButton button) {
        button.setLayoutX(MENU_BUTTON_START_X);
        button.setLayoutY(MENU_BUTTON_START_Y + menuButtons.size() * 100);
        menuButtons.add(button);
        mainPane.getChildren().add(button);
    }
    private void CreateButtons() {
        createStartButton();
        createScoresButton();
        createHelpButton();
        createCreditsButton();
        createExitButton();
    }
    private void createStartButton() {
        GameButton startButton = new GameButton("PLAY");
        AddMenuButtons(startButton);

        startButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                GameView gameView = new GameView();
                gameView.createNewGame(mainStage);
            }
        });
    }
    private void createScoresButton() {
        GameButton scoresButton = new GameButton("SCORES");
        AddMenuButtons(scoresButton);

        scoresButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (!isscorebuttonclicked)
                try {
                    BufferedReader reader=new BufferedReader(new FileReader("highscore.txt"));
                    String str;
                    while ((str=reader.readLine())!=null) {
                            observableList.add(str);
                    }
                    reader.close();
                    listView.setItems(observableList.sorted());
                } catch (FileNotFoundException e) {
                    NohighscoreException nohighscoreException=new NohighscoreException();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                isscorebuttonclicked=true;
                File file=new File("highscore.txt");
                if (file.exists())
                    showSubScene(scoreSubscene);

            }
        });
    }
    private void createHelpButton() {
        GameButton helpButton = new GameButton("HELP");
        AddMenuButtons(helpButton);

        helpButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                showSubScene(helpSubscene);

            }
        });
    }
    private void createCreditsButton() {

        GameButton creditsButton = new GameButton("CREDITS");
        AddMenuButtons(creditsButton);

        creditsButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                showSubScene(creditsSubscene);

            }
        });
    }
    private void createExitButton() {
        GameButton exitButton = new GameButton("EXIT");
        AddMenuButtons(exitButton);

        exitButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                mainStage.close();

            }
        });
    }
    private void createBackground() {
        Image backgroundImage = new Image("/Images/wilkizajac.jpg", 1024, 768, false, false);
        BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, null);
        mainPane.setBackground(new Background(background));
    }
    private void createLogo() {
        ImageView logo = new ImageView("/Images/nupogodi1.png");
        logo.setLayoutX(240);
        logo.setLayoutY(0);
        logo.setFitHeight(150);
        logo.setFitWidth(600);


        logo.setOnMouseEntered(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                logo.setEffect(new DropShadow());

            }
        });

        logo.setOnMouseExited(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                logo.setEffect(null);

            }
        });

        mainPane.getChildren().add(logo);

    }
}
