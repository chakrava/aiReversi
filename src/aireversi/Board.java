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

    public void printValues() {
        for (int i = 0; i < board.length; i++) {
            {
                board[i].setValue(i);
            }
        }
        printBoard();
    }

    public static int translate(int x, int y) {
        throw new RuntimeException("Not implemented");
        //return (y * 10) + x;
    }

    public static int[] translate(int p) {
        int x[] = new int[2];
        x[0] = p - (p / 10) * 10 - 1;
        x[1] = p / 10 - 1;
        return x;
    }

    public Board(Node[] boardInc) {
        for (int i = 0; i < board.length; i++) {
            board[i] = new Node(boardInc[i].getValue());
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

    public int getScore(int x) {
        return getNum(x) + (5 * getEdge(x));
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
