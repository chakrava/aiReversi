package aireversi;

import java.util.ArrayList;

/**
 *
 * @author Erik Storla
 */
public class AlphaBetaPrune {

    public Move bestMove;
    private final int maxHeight;
    int depth;

    /**
     * Constructs an AlphaBetaPrune object
     *
     * @param maxH the highest height the algorithm will search
     */
    public AlphaBetaPrune(int maxH) {
        bestMove = new Move();
        bestMove.position = -1;
        maxHeight = maxH;
    }

    /**
     * Performs an alpha-beta pruning search for the best possible move.
     *
     * @param parent the parent board that is being expanded
     * @param color the color of the player whose term is being calculated at
     * this node
     * @param alpha all valid values must be greater than alpha
     * @param beta all valid values must be less or equal to beta
     * @param height the height of this node
     * @return
     */
    public int abPrune(Board parent, int color, int alpha, int beta, int height) {
        ArrayList<Move> moves = parent.getMoves(color);

        if (height == 0 || moves.isEmpty()) {
            return parent.getFullScore(color);
        }
        if (bestMove.position == -1 && !moves.isEmpty()) {
            bestMove = moves.get(0);
        }

        if (color == 1) {
            //for (int i = 0; i < moves.size(); i++) {
            Move move;
            while (!moves.isEmpty()) {
                //Move move = moves.get(i);
                move = moves.remove((int) (Math.random() * moves.size()));
                Board newBoard = new Board(parent.board);
                newBoard.putPiece(move);
                move.score = newBoard.getFullScore(color);
                alpha = move.score;

                int prune = abPrune(newBoard, -1, alpha, beta, height - 1);
                if (prune > alpha) {
                    alpha = prune;
                    if (height == maxHeight) {
                        bestMove = move;
                    }
                }
                if (alpha < beta) {
                    break;
                }
            }
            return alpha;
        } else {//if (color == -1) {
            Move move;
            while (!moves.isEmpty()) {
                move = moves.remove((int) (Math.random() * moves.size()));
                Board newBoard = new Board(parent.board);
                newBoard.putPiece(move);
                move.score = newBoard.getFullScore(color);
                beta = move.score;

                int prune = abPrune(newBoard, 1, alpha, beta, height - 1);
                if (prune < beta) {
                    beta = prune;
                    if (height == maxHeight) {
                        bestMove = move;
                    }
                }
                if (alpha < beta) {
                    break;
                }
            }
            return beta;
        }
    }
}
