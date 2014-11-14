/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aireversi;

import java.util.Comparator;

/**
 *
 * @author ckiemnstr
 */
public class Move {

    public int position;
    public int color;
    public int score;

    static class compareScore implements Comparator<Move> {

        @Override
        public int compare(Move o1, Move o2) {
            if (o1.score == o2.score) {
                return 0;
            } else if (o1.score > o2.score) {
                return 1;
            } else {
                return -1;
            }
        }

    }
}
