package aireversi;

import java.util.ArrayList;

/**
 *
 * @author Erik Storla
 */
public class Board {

    public Node[] board = new Node[100];//64];
    //public int[] directions = {-9, -8, -7, -1, 1, 7, 8, 9};
    public int[] directions = {-11, -10, -9, -1, 1, 9, 10, 11};

//  private static final int edgePositions[] = {0, 1, 2, 3, 4, 5, 6, 7, 8, 15,
//        16, 23, 24, 31, 32, 39, 40, 47, 48, 55, 56, 57, 58, 59, 60, 61, 62, 63};
    /**
     * Constructs a Board object representing a reversi board. The board is an
     * array of 100 Nodes that represents the 8x8 board with a border (10x10).
     * The top-left Node in the play area is 11, the top-right is 18...the
     * bottom right (last) is 88.
     */
    public Board() {
        for (int i = 0; i < board.length; i++) {
            board[i] = new Node();
            //if (i == 27 || i == 36) {
            int row = i / 10;
            int column = i % 10;

            if (i == 45 || i == 54) {
                board[i].setBlack();
                //} else if (i == 28 || i == 35) {
            } else if (i == 44 || i == 55) {
                board[i].setWhite();
            } else if (column == 0 || column == 9) {
                board[i].setValue(7);
            } else if (row == 0 || row == 9) {
                board[i].setValue(7);
            }
        }
    }

    /**
     * Used to translate a set of (x,y) values into its position in the array.
     *
     * @deprecated
     * @param x the horizontal position
     * @param y the vertical position
     * @return the integer
     */
    public static int translate(int x, int y) {
        throw new RuntimeException("Not implemented");
        //return (y * 10) + x;
    }

    /**
     * Translates an array position into its more human-readable (x,y) format
     *
     * @param p the position in the array
     * @return [horizontal value, vertical value]
     */
    public static int[] translate(int p) {
        int x[] = new int[2];
        x[0] = p - (p / 10) * 10 - 1;
        x[1] = p / 10 - 1;
        return x;
    }

    /**
     * Makes a copy of a Board
     *
     * @param boardInc the board to make a copy of
     */
    public Board(Node[] boardInc) {
        for (int i = 0; i < board.length; i++) {
            board[i] = new Node(boardInc[i].getValue());
        }
    }

    /**
     * Returns the number of times a value appears on the Board
     *
     * @param x the value to be counted
     * @return the number of times x appears
     */
    public int getNum(int x) {
        int total = 0;
        for (Node n : board) {
            if (n.getValue() == x) {
                total++;
            }
        }
        return total;
    }

    /**
     * Returns the number of times a value appears on an edge of the Board
     *
     * @param x the value to be counted
     * @return the number of times x appears on an edge
     */
    public int getEdge(int x) {
        int total = 0;

        for (int i = 0; i < board.length; i++) {
            if (board[i].getValue() == x) {
                int row = i / 10;
                int column = i % 10;
                if (row == 1 || row == 9
                        || column == 1 || column == 9) {
                    total++;
                }
            }
        }

        return total;
    }

    /**
     * Returns the number of times a value appears on an row/column just inside
     * the edge.
     *
     * @param x the value to be counted
     * @return the number of times x appears
     */
    public int getInsideEdge(int x) {
        int total = 0;

        for (int i = 0; i < board.length; i++) {
            if (board[i].getValue() == x) {
                int row = i / 10;
                int column = i % 10;
                if (row == 2 || row == 8
                        || column == 2 || column == 8
                        && (row != 1 || row != 9
                        || column != 1 || column != 9)) {
                    total++;
                }
            }
        }

        return total;
    }

    public int getFullScore(int x) {
        return getScore(x) - getScore(-x);
    }

    public int getScore(int x) {
        return getNum(x) + (2 * getEdge(x)) + (-1 * getInsideEdge(x));
    }

    public Board putPiece(int x, int y, int color) {
        Move move = new Move();
        move.position = translate(x, y);
        move.color = color;

        return putPiece(move);
    }

    public Board putPiece(Move move) {
        Board newBoard = new Board(this.board);
        ArrayList<Integer> flipped = getFlipped(move.position, move.color);

        if (flipped != null) {
            newBoard.board[move.position].setValue(move.color);
            for (int n : flipped) {
                newBoard.board[n].setValue(move.color);
            }
        }

        return newBoard;
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
            int index = positionToCheck;// + direction;

            while (board[index += direction].getValue() == (color * -1)) {
                distance++;
            }

            if (board[index].getValue() == color && distance > 1) {
                while (distance-- > 1) {
                    index -= direction;
                    flipped.add(index);
                }
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
            int column = i % 10;
            int row = i / 10;

            if ((i) % 10 == 0 && row > 0 && row < 9) {
                temp += "[" + ((i / 10) - 1) + "] ";
            }
            if (column != 0 && column != 9
                    && row != 0 && row != 9) {

                String n = " ";
                if (board[i].isBlack()) {
                    n = "X";
                } else if (board[i].isWhite()) {
                    n = "O";
                }
                //n = board[i].getValue() + "";
                temp += "[" + n + "] ";

                if (i + 1 > 0 && (i + 1) % 10 == 0) {
                    temp += "\n";
                }
            }
            if (i + 1 > 0 && (i + 1) % 10 == 0 && row != 0) {
                temp += "\n";
            }
        }

        temp += "Black: " + this.getNum(1) + " [" + this.getScore(1) + "]"
                + "\nWhite: " + this.getNum(-1) + " [" + this.getScore(-1) + "]" + "\n";

        return temp;
    }
}
