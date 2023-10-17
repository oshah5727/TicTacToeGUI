import javax.swing.*;

public class TicTacToeRunner {

    public static void main(String[] args) {
     TicTacToeFrame tttFrame = new TicTacToeFrame();
        tttFrame.setTitle("Tic Tac Toe Game");
        tttFrame.setSize(800,800);
        tttFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        tttFrame.setVisible(true);
    }
}