package aireversi;

import java.util.ArrayList;

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
        int iteration = 0;
        while (initialBoard.getNumBlanks() > 0 && iteration < 200) {
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
//        initialBoard.printBoard();
//        System.exit(0);
        //state.populateChildren(1);

        Board pl=null;// = initialBoard.abprune(initialBoard, initialBoard, 3, Integer.MIN_VALUE, Integer.MAX_VALUE, color);//, initialBoard.steps);
        initialBoard.populateChildren(color,true);
        for (Board br : initialBoard.children) {
            pl = br.abprune(initialBoard, 3, Integer.MIN_VALUE, Integer.MAX_VALUE, color);//, initialBoard.steps);
        }
        //float pl = initialBoard.abprune(initialBoard, 4, Integer.MIN_VALUE, Integer.MAX_VALUE, color);//, initialBoard.steps);

        System.out.println(pl);
        //System.out.println(Board.moveToMake[0]+","+Board.moveToMake[1]);
        pl.printMove();

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
