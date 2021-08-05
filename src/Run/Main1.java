package Run;


import javafx.application.Application;
import javafx.stage.Stage;
import Game.View;

public class Main1 extends Application {
    @Override
    public void start(Stage primarystage){
        try{
            View view=new View();
            primarystage=view.getMainStage();
            primarystage.show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
