package aireversi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 *
 * @author Erik Storla
 */
public class AiReversi {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println("X\tblack\nO\twhite");

        Board initialBoard = new Board();
        //State state = new State(initialBoard);

        initialBoard.printBoard();
        long start = System.currentTimeMillis();

        int color = 1;
        int iteration = 100;
        while (initialBoard.getNumBlanks() > 0 && iteration < 100) {
            int x = (int) (Math.random() * 8);
            int y = (int) (Math.random() * 8);

            if (initialBoard.putPiece(x, y, color)) {
                System.out.println(x + "," + y + " " + color);

                color *= -1;
                initialBoard.printBoard();
                //iteration = 0;
            }
            iteration++;
        }

//        initialBoard.populateChildren(color);
//        for(Board br:initialBoard.children){
//            br.printBoard();
//        }
        iteration = 0;//50000;
        Board pl = null;// = initialBoard.abprune(initialBoard, initialBoard, 3, Integer.MIN_VALUE, Integer.MAX_VALUE, color);//, initialBoard.steps);
        while (initialBoard.getNumBlanks() > 0 && iteration < Math.pow(initialBoard.getBoard().length, 2)) {
            initialBoard.populateChildren(color, true);
            for (Board br : initialBoard.children) {
                Board tempBoard = br.abprune(initialBoard, 2, Integer.MIN_VALUE, Integer.MAX_VALUE, color);//, initialBoard.steps);
                if (tempBoard != null) {
                    pl = tempBoard;
                }
            }

            String colorString = "B";
            if (color == -1) {
                colorString = "W";
            }
            System.out.print(colorString + ": ");
            pl.printMove();

            initialBoard.putPiece(pl.move[0], pl.move[1], color);
            initialBoard.printBoard();
            color *= -1;

            iteration++;
        }

//        for(Integer[] i:pl.steps){
//            System.out.println(i);
//        }
//        for (Board move : initialBoard.children) {
//            move.printBoard();
//            move.printScore();
//        }
        //initialBoard.printBoard();
        System.out.println("in " + (System.currentTimeMillis() - start) / 1000.0 + " seconds");

    }

}
