package aireversi;

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

        while (initialBoard.getNum(0) > 0 && iteration++ < 300) {
            AlphaBetaPrune abp = new AlphaBetaPrune(7);

            String player = "B";
            if (color == -1) {
                player = "W";
            }

            abp.abPrune(initialBoard, color, Integer.MIN_VALUE, Integer.MAX_VALUE, 7);
            if (abp.bestMove.position == -1) {
                System.out.println("No moves available for " + player + "!");
            } else {
                int AIposition[] = Board.translate(abp.bestMove.position);

                System.out.println(player + " chooses: "
                        + AIposition[0] + "," + AIposition[1]
                        + " [" + abp.bestMove.position + "]");

                initialBoard = initialBoard.putPiece(abp.bestMove);

                initialBoard.printBoard();
            }

            color *= -1;
        }
        if (initialBoard.getNum(1) == initialBoard.getNum(-1)) {
            System.out.println("Tie!");
            System.exit(0);
        }
        String winner = "Black";
        if (initialBoard.getNum(-1) > initialBoard.getNum(1)) {
            winner = "White";
        }
        System.out.println(winner + " wins!"); //end
    }
}
