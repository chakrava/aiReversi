/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aireversi;

import java.util.ArrayList;

/**
 *
 * @author ckiemnstr
 */
public class AlphaBetaPrune {

    public Move bestMove;
    private int INF = Integer.MAX_VALUE;
    private int maxDepth;
    int depth;

    public AlphaBetaPrune(int maxD) {
        bestMove = new Move();
        bestMove.position = -1;
        maxDepth = maxD;
    }

    public int abPrune(Board parent, int color, int alpha, int beta, int depth) {
        ArrayList<Move> moves = parent.getMoves(color);
        //System.out.println(moves.size()+" moves for "+color);

        if (depth == 0 || moves.isEmpty()) {
            return parent.getScore(color);
        }
        if (bestMove.position == -1 && !moves.isEmpty()) {
            bestMove = moves.get(0);
        }

        if (color == 1) {
            for (int i = 0; i < moves.size(); i++) {
                Move move = moves.get(i);
                Board newBoard = new Board(parent.board);
                newBoard.putPiece(move);
                move.score = newBoard.getScore(color);
                alpha = move.score;

                //alpha = Integer.max(alpha, abPrune(newBoard, -1, alpha, beta, depth - 1));
                int prune = abPrune(newBoard, -1, alpha, beta, depth - 1);
                //System.out.println("a- a: " + alpha+" b: "+beta);
                if (prune > alpha) {
                    alpha = prune;
                    if (depth == maxDepth) {
                        bestMove = move;
                    }
                }
                //System.out.println("Alpha to: " + prune);
                //System.out.println("a: "+alpha+" b: "+beta);
                if (alpha < beta) {
                    break;
                }
            }
            //System.out.println("Returning: "+alpha);
            return alpha;
        } else {//if (color == -1) {
            for (int i = 0; i < moves.size(); i++) {
                Move move = moves.get(i);
                Board newBoard = new Board(parent.board);
                newBoard.putPiece(move);
                move.score = newBoard.getScore(color);
                beta = move.score;

                //beta = Integer.max(beta, abPrune(newBoard, 1, alpha, beta, depth - 1));
                int prune = abPrune(newBoard, 1, alpha, beta, depth - 1);
                //System.out.println("b- a: " + alpha+" b: "+beta);
                if (prune < beta) {
                    beta = prune;
                    if (depth == maxDepth) {
                        bestMove = move;
                    }
                }
                //System.out.println("Beta to: " + prune);
                //System.out.println("a: "+alpha+" b: "+beta);
                if (alpha < beta) {
                    break;
                }
            }
            return beta;
        }
        //return returnBoard;//
    }
}
