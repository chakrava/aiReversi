package aireversi2;

import java.util.ArrayList;

/**
 *
 * @author Erik Storla
 */
public class Board {

    public Node[] board = new Node[64];
    public int[] directions = {-9, -8, -7, -1, 1, 7, 8, 9};

    public Board() {
        for (int i = 0; i < board.length; i++) {
            board[i] = new Node();
            if (i == 28 || i == 37) {
                board[i].setBlack();

            } else if (i == 29 || i == 36) {
                board[i].setWhite();
            }
        }
    }

    public Board(Node[] boardInc) {
        for (int i = 0; i < board.length; i++) {
            board[i].setValue(boardInc[i].getValue());
        }
    }

    public int getNum(int x) {
        int total = 0;
        for (Node n : board) {
            if (n.getValue() == x) {
                total++;
            }
        }
        return total;
    }

    public ArrayList<Move> getMoves(int color) {
        ArrayList<Move> moves = new ArrayList<>();
        for (int i = 0; i < board.length; i++) {
            if (board[i].isBlank()) {
                ArrayList<Integer> flipped = getFlipped(i, color);
                if (flipped.size() > 0) {
                    Move move = new Move();
                    move.color = color;
                    move.position = i;
                    move.score = 0;
                    moves.add(move);
                }
            }
        }

        return moves;
    }

    public ArrayList<Integer> getFlipped(int positionToCheck, int color) {
        ArrayList<Integer> flipped = new ArrayList<>();

        for (int direction : directions) {
            int distance = 1;
            int index = positionToCheck;
            while (board[index].getValue() == (color * -1)) {
                distance++;
            }
            if (board[index].getValue() == color) {
                index -= direction;
                flipped.add(index);
            }

        }

        return flipped;
    }

    public void printBoard() {
        System.out.println(this);
    }

    @Override
    public String toString() {
        String temp = "    [0] [1] [2] [3] [4] [5] [6] [7]\n";

        for (int i = 0; i < board.length; i++) {
            temp += "[" + i + "] ";
            String n = " ";
            if (board[i].isBlack()) {
                n = "X";
            } else if (board[i].isWhite()) {
                n = "O";
            }
            temp += "[" + n + "] ";
        }
        temp += "\n";
    }
    temp += "Black: " + this.getNumBlack() + " [" + this.getScore(1) + "]"
            + "\nWhite: " + this.getNumWhite() + " [" + this.getScore(-1) + "]" + "\n" ;

    return temp ;
}
}
