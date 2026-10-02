package game;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class FarmerMazeGame {

     public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Farmer Maze Game");

            GameBase game = new Farmer();

            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1200, 720);
            frame.setLocationRelativeTo(null);
            frame.setResizable(false);
            frame.add(game); 

            frame.setVisible(true);

            game.init();
            game.requestFocus();
        });
    }
}




