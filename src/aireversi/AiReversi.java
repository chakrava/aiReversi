package aireversi;

import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author Erik Storla
 */
public class AiReversi {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Board initialBoard = new Board();
        initialBoard.printBoard();

        int iteration = 0;
        int color = 1;
        Move introMove = new Move();
        introMove.color = 1;
        introMove.position = 34;

        while (initialBoard.getNum(0) > 0 && iteration++ < 200) {
            AlphaBetaPrune abp = new AlphaBetaPrune(5);

            abp.abPrune(initialBoard, color, Integer.MIN_VALUE, Integer.MAX_VALUE, 6);
            if (abp.bestMove.position == -1) {
                break;
            }

            int AIposition[] = Board.translate(abp.bestMove.position);

            String player = "B";
            if (color == -1) {
                player = "W";
            }
            System.out.println(player + " chooses: "
                    + AIposition[0] + "," + AIposition[1]
                    + " [" + abp.bestMove.position + "]");

            initialBoard = initialBoard.putPiece(abp.bestMove);

            initialBoard.printBoard();

            color *= -1;
        }
        //end
    }
}
