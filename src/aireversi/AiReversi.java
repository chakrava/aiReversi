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

//        System.out.println("Moves:");
//        for (Move m : initialBoard.getMoves(1)) {
//            System.out.print(m.position + " ");
//        }
        //System.out.println();
        //initialBoard.putPiece(4, 2, 1).printBoard();
        int iteration = 0;
        int color = 1;
        Move introMove = new Move();
        introMove.color = 1;
        introMove.position = 34;
        //initialBoard = initialBoard.putPiece(introMove);
        //color = -1;

        while (initialBoard.getNum(0) > 0 && iteration++ < 200) {
            AlphaBetaPrune abp = new AlphaBetaPrune(5);

            abp.abPrune(initialBoard, color, Integer.MIN_VALUE, Integer.MAX_VALUE, 6);
            if(abp.bestMove.position==-1){
                break;
            }
            
            int AIposition[] = Board.translate(abp.bestMove.position);

            System.out.println("ab " + color + " chooses: " + AIposition[0] + "," + AIposition[1] + " [" + abp.bestMove.position + "]");
            //Move abMove = new Move();
            //abMove.position = abp.bestMove;
            //abMove.color = color;

//            ArrayList<Move> moves = initialBoard.getMoves(color);
//            Collections.sort(moves, new Move.compareScore());
//
//            if (moves.isEmpty()) {
//                break;
//            }
//
//            int position[] = Board.translate(moves.get(0).position);
//            initialBoard = initialBoard.putPiece(moves.get((int) (Math.random() * moves.size())));
            initialBoard = initialBoard.putPiece(abp.bestMove);

//            String player = "B";
//            if (color == -1) {
//                player = "W";
//            }
            //System.out.println(player + ": " + position[0] + "," + position[1]);
            initialBoard.printBoard();

            color *= -1;
        }
    }

}
