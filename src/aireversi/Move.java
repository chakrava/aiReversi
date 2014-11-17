package aireversi;

import java.util.Comparator;

/**
 * Represents a Move in Reversi.
 *
 * @author Erik Storla
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
