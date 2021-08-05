package Game;

import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import Elements.GameSmallInfoLabel;

import java.io.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;


public class GameView{

    private AnchorPane gamePane;
    private Scene gameScene;
    private Stage gameStage;

    private AnchorPane highscorePane;
    private Scene highscoreScene;
    private Stage highscoreStage;

    private Stage menuStage;


    private boolean isLeftKeyPressed;
    private boolean isRightKeyPressed;
    private boolean isUpKeyPressed;
    private boolean isDownKeyPressed;
    private boolean isShiftKeyPressed;
    private boolean isQKeyPressed;
    private boolean isCtrlKeyPressed;

    private AnimationTimer gameTimer1;
    private AnimationTimer gameTimer2;
    private Thread thread=null;

    private static final int GAME_WIDTH = 1280;
    private static final int GAME_HEIGHT = 720;

    private final int RIGHTWOLFX=690;
    private final int RIGHTWOLFY=300;
    private final int LEFTWOLFX=225;
    private final int LEFTWOLFY=300;

    private int delay=500000000;

    private static final int HIGHSCORE_WIDTH = 440;
    private static final int HIGHSCORE_HEIGHT = 180;
    private TextField nametextfield;
    private Label highscoreinfo;
    private Button highscorebutton;
    private Text highscoretext;
    private ArrayList<String> highscoreList=new ArrayList<>();

    private ImageView wolf;
    private final String rightupurl= "/Images/rightupwolf.png";
    private final String rightdownurl= "/Images/rightdownwolf.png";
    private final String leftupurl= "/Images/leftupwolf.png";
    private final String leftdownurl= "/Images/leftdownwolf.png";
    private final Image rightupimage=new Image(rightupurl);
    private final Image rightdownimage=new Image(rightdownurl);
    private final Image leftdownimage=new Image(leftdownurl);
    private final Image leftupimage=new Image(leftupurl);
    private final String lifeurl= "/Images/life.png";
    private final Image lifeimage=new Image(lifeurl);
    private final static String BACKGROUND_IMAGE = "/Images/background.png";
    private final String eggurl= "/Images/egg1.png";
    private final Image eggimage=new Image(eggurl);

    private GameSmallInfoLabel pointsLabel;
    private ImageView[] playerLifes;
    private int playerLife;
    private int points=0;
    private int pointargument=10;

    private ImageView egg;
    private DecimalFormat decimalFormat=new DecimalFormat("0000");
    private DecimalFormat decimalFormat1=new DecimalFormat("00");
    private String ddsecond,ddminutes,ddhours;
    private int second=0;
    private int minute=0;
    private int hour=0;
    private String ddpoint;
    private Random randomeggposiotion;
    private int randomegg;
    private boolean run=false;
    private TranslateTransition translateTransition;
    private RotateTransition rotateTransition;
    private ParallelTransition parallelTransition;
    private Circle circle;
    private double duration=10000;
    public GameView(){
        initializeStage();
        initializeStage2();
        createKeyListeners();
    }
    public void createNewGame(Stage menuStage) {
        this.menuStage = menuStage;
        this.menuStage.hide();
        createBackground();
        createPointsandLives();
        egg();
        createWolf();
        run=true;
        gameStage.show();
        gameStage.setResizable(false);
        setThread();
        createGameLoop();
    }
    private void createhighscoreelements(){
        highscoreinfo=new Label("END GAME");
        highscoreinfo.setLayoutX(130);
        highscoreinfo.setLayoutY(20);
        highscoreinfo.setFont(Font.loadFont(getClass().getResourceAsStream("/Images/font.ttf"), 30));
        highscoretext=new Text("Name:");
        highscoretext.setLayoutX(60);
        highscoretext.setLayoutY(116);
        highscorebutton=new Button("ADD");
        highscorebutton.setLayoutX(280);
        highscorebutton.setLayoutY(100);
        nametextfield=new TextField();
        nametextfield.setLayoutX(100);
        nametextfield.setLayoutY(100);
        highscorebutton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (nametextfield.getText()!=""){
                    if (points!=0)
                    highscoreList.add(nametextfield.getText()+"\t\t\t\t\t"+getDdpoint()+"\t\t\t\t\t"+getDdhours()+":"+getDdminutes()+":"+getDdsecond());
                    else
                        highscoreList.add(nametextfield.getText()+"\t\t\t\t\t"+"0000"+"\t\t\t\t\t"+getDdhours()+":"+getDdminutes()+":"+getDdsecond());
                    try {
                        BufferedWriter bufferedWriter=new BufferedWriter(new FileWriter("highscore.txt"));
                        for (int i=0;i<highscoreList.size();i++){
                            bufferedWriter.write(highscoreList.get(i));
                            bufferedWriter.newLine();
                        }
                        bufferedWriter.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    highscoreStage.hide();
                    menuStage.show();
                }
            }
        });
        highscorePane.getChildren().addAll(nametextfield,highscoreinfo,highscorebutton,highscoretext);
    }
    private void readgamescorelist(){
        try {
            BufferedReader reader=new BufferedReader(new FileReader("highscore.txt"));
            String str;
            while ((str=reader.readLine())!=null)
                highscoreList.add(str);
            reader.close();
        } catch (FileNotFoundException e) {
            NohighscoreException nohighscoreException=new NohighscoreException();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void initializeStage2(){
        highscorePane=new AnchorPane();
        highscoreScene=new Scene(highscorePane,HIGHSCORE_WIDTH,HIGHSCORE_HEIGHT);
        highscoreStage=new Stage();
        highscoreStage.setScene(highscoreScene);
        createhighscoreelements();
    }
    private void initializeStage() {
        gamePane = new AnchorPane();
        gameScene = new Scene(gamePane, GAME_WIDTH, GAME_HEIGHT);
        gameStage = new Stage();
        gameStage.setScene(gameScene);
    }
    private void createKeyListeners(){
        gameScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode()== KeyCode.LEFT){
                    isLeftKeyPressed=true;
                }
                if (event.getCode()== KeyCode.RIGHT){
                    isRightKeyPressed=true;
                }
                if (event.getCode()== KeyCode.DOWN){
                    isDownKeyPressed=true;
                }
                if (event.getCode()== KeyCode.UP){
                    isUpKeyPressed=true;
                }
                if (event.getCode()==KeyCode.SHIFT){
                    isShiftKeyPressed=true;
                }
                if (event.getCode()==KeyCode.Q){
                    isQKeyPressed=true;
                }
                if (event.getCode()==KeyCode.CONTROL){
                    isCtrlKeyPressed=true;
                }
            }
        });
        gameScene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.LEFT) {
                    isLeftKeyPressed = false;
                }
                if (event.getCode() == KeyCode.RIGHT) {
                    isRightKeyPressed = false;
                }
                if (event.getCode() == KeyCode.DOWN) {
                    isDownKeyPressed = false;
                }
                if (event.getCode() == KeyCode.UP) {
                    isUpKeyPressed = false;
                }
                if (event.getCode()==KeyCode.SHIFT){
                    isShiftKeyPressed=false;
                }
                if (event.getCode()==KeyCode.Q){
                    isQKeyPressed=false;
                }
                if (event.getCode()==KeyCode.CONTROL){
                    isCtrlKeyPressed=false;
                }
            }
        });
    }
    private void createPointsandLives(){
        playerLife=2;
        pointsLabel=new GameSmallInfoLabel("0000");
        pointsLabel.setLayoutX(900);
        pointsLabel.setLayoutY(0);
        gamePane.getChildren().add(pointsLabel);
        playerLifes=new ImageView[3];
        for (int i=0;i<playerLifes.length;i++){
            playerLifes[i]=new ImageView(lifeimage);
            playerLifes[i].setLayoutX(900+(i*80));
            playerLifes[i].setLayoutY(100);
            gamePane.getChildren().add(playerLifes[i]);
        }
    }
    private void egg(){
        randomeggposiotion=new Random();
        egg=new ImageView(eggimage);
        randomegg=randomeggposiotion.nextInt(4);
        translateTransition=new TranslateTransition(Duration.millis(duration),egg);
        rotateTransition=new RotateTransition(Duration.millis(duration),egg);
        parallelTransition=new ParallelTransition();
        parallelTransition.getChildren().addAll(translateTransition,rotateTransition);
        switch (randomegg){
            case 0 :
                translateTransition.setFromX(80);
                translateTransition.setFromY(275);
                translateTransition.setToX(300);
                translateTransition.setToY(385);
                rotateTransition.setFromAngle(0);
                rotateTransition.setToAngle(720);
                parallelTransition.play();
                gamePane.getChildren().add(egg);
                break;
            case 1 :
                translateTransition.setFromX(80);
                translateTransition.setFromY(415);
                translateTransition.setToX(320);
                translateTransition.setToY(535);
                rotateTransition.setFromAngle(0);
                rotateTransition.setToAngle(720);
                parallelTransition.play();
                gamePane.getChildren().add(egg);
                break;
            case 2 :
                translateTransition.setFromX(1150);
                translateTransition.setFromY(275);
                translateTransition.setToX(950);
                translateTransition.setToY(375);
                rotateTransition.setFromAngle(0);
                rotateTransition.setToAngle(720);
                parallelTransition.play();
                gamePane.getChildren().add(egg);
                break;
            case 3 :
                translateTransition.setFromX(1150);
                translateTransition.setFromY(415);
                translateTransition.setToX(960);
                translateTransition.setToY(510);
                rotateTransition.setFromAngle(0);
                rotateTransition.setToAngle(720);
                parallelTransition.play();
                gamePane.getChildren().add(egg);
                break;
        }
    }
    private void createBackground() {
        ImageView backgroundImage = new ImageView(BACKGROUND_IMAGE);
        backgroundImage.setLayoutX(0);
        backgroundImage.setLayoutY(200);
        backgroundImage.setFitWidth(1280);
        backgroundImage.setFitHeight(520);

        gamePane.getChildren().add(backgroundImage);
    }
    private void createWolf() {
        wolf = new ImageView(rightdownimage);
        wolf.setLayoutX(RIGHTWOLFX);
        wolf.setLayoutY(RIGHTWOLFY-30);

        gamePane.getChildren().add(wolf);
    }
    private void movewolf(){
        if (isLeftKeyPressed&&isUpKeyPressed){
            wolf.setImage(leftupimage);
            wolf.setLayoutX(LEFTWOLFX+10);
            wolf.setLayoutY(LEFTWOLFY);
        }
        if (isLeftKeyPressed&&isDownKeyPressed){
            wolf.setImage(leftdownimage);
            wolf.setLayoutX(LEFTWOLFX-10);
            wolf.setLayoutY(LEFTWOLFY-40);
        }
        if (isRightKeyPressed&&isDownKeyPressed){
            wolf.setImage(rightdownimage);
            wolf.setLayoutX(RIGHTWOLFX);
            wolf.setLayoutY(RIGHTWOLFY-30);
        }
        if (isRightKeyPressed&&isUpKeyPressed){
            wolf.setImage(rightupimage);
            wolf.setLayoutX(RIGHTWOLFX);
            wolf.setLayoutY(RIGHTWOLFY);
        }
    }
    private void chechcolisionforpoint(){
        circle=new Circle();
        circle.setRadius(35);
        if (wolf.getImage()==rightupimage){
            circle.setCenterX(950);
            circle.setCenterY(400);
            if (circle.intersects(egg.getBoundsInParent())){
                parallelTransition.stop();
                gamePane.getChildren().remove(egg);
                points++;
                egg();
                ddpoint=decimalFormat.format(points);
                pointsLabel.setText(ddpoint);
            }
        }
        if (wolf.getImage()==rightdownimage){
            circle.setCenterX(950);
            circle.setCenterY(530);
            if (circle.intersects(egg.getBoundsInParent())){
                parallelTransition.stop();
                gamePane.getChildren().remove(egg);
                points++;
                egg();
                ddpoint=decimalFormat.format(points);
                pointsLabel.setText(ddpoint);
            }
        }
        if (wolf.getImage()==leftupimage){
            circle.setCenterX(320);
            circle.setCenterY(400);
            if (circle.intersects(egg.getBoundsInParent())){
                parallelTransition.stop();
                gamePane.getChildren().remove(egg);
                points++;
                egg();
                ddpoint=decimalFormat.format(points);
                pointsLabel.setText(ddpoint);
            }
        }
        if (wolf.getImage()==leftdownimage){
            circle.setCenterX(320);
            circle.setCenterY(530);
            if (circle.intersects(egg.getBoundsInParent())){
                parallelTransition.stop();
                gamePane.getChildren().remove(egg);
                points++;
                egg();
                ddpoint=decimalFormat.format(points);
                pointsLabel.setText(ddpoint);
            }
        }
    }
    private void checkcolisiorforloselife(){
        if (egg.getBoundsInParent().intersects(300,385,0.1,0.1))
            removelife();
        if (egg.getBoundsInParent().intersects(320,535,0.1,0.1))
            removelife();
        if (egg.getBoundsInParent().intersects(950,375,0.1,0.1))
            removelife();
        if (egg.getBoundsInParent().intersects(960,510,0.1,0.1))
            removelife();
    }

    private void removelife(){
        gamePane.getChildren().remove(playerLifes[playerLife]);
        playerLife--;
        gamePane.getChildren().remove(egg);
        egg();
        if(playerLife < 0||points==9999) {
            gameStage.close();
            gameTimer1.stop();
            gameTimer2.stop();
            run=false;
            readgamescorelist();
            highscoreStage.show();
        }
    }
    private void increasespeed(){
        if (points==pointargument&&duration>2000){
            duration-=200;
            pointargument+=10;
        }
    }
    private void addtime(){
        second++;
        if(second==60){
            second=0;
            minute++;
        }
        if(minute==60){
            minute=0;
            hour++;
        }
        ddsecond=decimalFormat1.format(second);
        ddminutes=decimalFormat1.format(minute);
        ddhours=decimalFormat1.format(hour);
    }
    private void createGameLoop() {

        gameTimer1=new AnimationTimer() {
            @Override
            public void handle(long l) {
                chechcolisionforpoint();
                checkcolisiorforloselife();
                increasespeed();
            }
        };
        gameTimer2=new AnimationTimer() {
            @Override
            public void handle(long l) {
                movewolf();
            }
        };
        gameTimer1.start();
        gameTimer2.start();
    }
    private void setThread(){
        new Thread(()->{
           while (run){
               addtime();
               try {
                   Thread.sleep(1000);
               } catch (InterruptedException e) {
                   e.printStackTrace();
               }
           }
        }).start();
    }

    public String getDdpoint() {
        return ddpoint;
    }

    public String getDdsecond() {
        return ddsecond;
    }

    public String getDdminutes() {
        return ddminutes;
    }

    public String getDdhours() {
        return ddhours;
    }
}