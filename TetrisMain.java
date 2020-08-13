// Kaushik Nadimpalli
// Assignment 3 Q1 - Tetris Game
// Purpose - Complete the tetris game from previous work

/* Main additions
  Speed, factor, Scoring factor, # of rows, Square size, Board length and width - User adjustable
*/

/* Improvements/Bugs for future fix
  Once setting is modified you cannot change it. Must recompile and run to change setting
  Point in Polygon algorithm not working properly. Detection/Changing to new shape
  not in current tetromino or next tetromino is not working as intended.
*/

import java.awt.*;
import java.awt.event.*;
public class TetrisMain extends Frame {
    TetrisMain() {
        super("Want to play tetris?");
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }});
        setSize(700, 1400);
        add("Center", new tetrisSchema(this));
        System.out.println("You are playing tetris. Good luck!");
        setVisible(true);
    }
        public static void main(String[] args) { new TetrisMain();}
}
