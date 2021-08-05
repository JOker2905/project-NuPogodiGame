package Game;

import javax.swing.*;

public class NohighscoreException extends Exception{
    public NohighscoreException(){
        JOptionPane.showMessageDialog(null,"I cant find highscore file","Nohighscorefile",JOptionPane.ERROR_MESSAGE);
    }
}
