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
    private final int maxDepth;
    int depth;

    public AlphaBetaPrune(int maxD) {
        bestMove = new Move();
        bestMove.position = -1;
        maxDepth = maxD;
    }

    public int abPrune(Board parent, int color, int alpha, int beta, int depth) {
        ArrayList<Move> moves = parent.getMoves(color);

        if (depth == 0 || moves.isEmpty()) {
            return parent.getScore(color);
        }
        if (bestMove.position == -1 && !moves.isEmpty()) {
            bestMove = moves.get(0);
        }

        if (color == 1) {
            //for (int i = 0; i < moves.size(); i++) {
            Move move;
            while(!moves.isEmpty()){
                //Move move = moves.get(i);
                move=moves.remove((int)(Math.random()*moves.size()));
                Board newBoard = new Board(parent.board);
                newBoard.putPiece(move);
                move.score = newBoard.getScore(color);
                alpha = move.score;

                int prune = abPrune(newBoard, -1, alpha, beta, depth - 1);
                if (prune > alpha) {
                    alpha = prune;
                    if (depth == maxDepth) {
                        bestMove = move;
                    }
                }
                if (alpha < beta) {
                    break;
                }
            }
            return alpha;
        } else {//if (color == -1) {
            //for (int i = 0; i < moves.size(); i++) {
            Move move;
            while(!moves.isEmpty()){
                //Move move = moves.get(i);
                move=moves.remove((int)(Math.random()*moves.size()));
                Board newBoard = new Board(parent.board);
                newBoard.putPiece(move);
                move.score = newBoard.getScore(color);
                beta = move.score;

                int prune = abPrune(newBoard, 1, alpha, beta, depth - 1);
                if (prune < beta) {
                    beta = prune;
                    if (depth == maxDepth) {
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
