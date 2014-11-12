package aireversi;

import java.util.ArrayList;

/**
 *
 * @author Erik Storla
 */
public class Board {

    private Node[][] board = new Node[8][8];
    public ArrayList<Board> children = new ArrayList<>();
    //public ArrayList<Integer[]> steps = new ArrayList<>();
    //int[] move = new int[2];
    int[] move = {-1, -1};
    //static Integer[] moveToMake = {-1, -1};

    float alpha = Integer.MIN_VALUE;
    float beta = Integer.MAX_VALUE;

    public Board() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                board[i][j] = new Node();
                if ((i == 3 && j == 3) || (i == 4 && j == 4)) {
                    board[i][j].setBlack();
                } else if ((i == 3 && j == 4) || (i == 4 && j == 3)) {
                    board[i][j].setWhite();
                }
            }
        }
        move[0] = -1;
        move[1] = -1;
    }

    public Board(Node[][] boardInc) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                board[i][j] = new Node(boardInc[i][j].getValue());
            }
        }
        move[0] = -1;
        move[1] = -1;
    }

    public Board abprune(Board parent, int depth, float a, float b, int color) {//, ArrayList<Integer[]> steps) {
        populateChildren(color);
        if (depth == 0 || children.isEmpty()) {
            return this;//.getScore(color);
        }

        if (color == 1) {
            for (int i = 0; i < children.size() && a < b; i++) {
                a = Float.max(a, children.get(i).abprune(this, depth - 1, a, b, -1).getScore(1));//,br.steps));
                //moveToMake[0] = children.get(i).move[0];
                //moveToMake[1] = children.get(i).move[1];
            }
            parent.alpha = a;
            return this;//a;
        } else {
            for (int i = 0; i < children.size() && a < b; i++) {
                b = Float.min(b, children.get(i).abprune(this, depth - 1, a, b, 1).getScore(1));//,br.steps));
            }
            parent.beta = b;
            return this;//b;
        }
    }

    public void populateChildren(int color) {
        this.populateChildren(color, false);
    }

    public void populateChildren(int color, boolean first) {
        //Board childBoard=new Board();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                //childBoard = new Board(this.getBoard());
                Board childBoard = this.checkPiece(i, j, color, first);
                if (childBoard != null) {
                    if (!first) {
                        childBoard.move[0] = this.move[0];
                        childBoard.move[1] = this.move[1];
                    }
                    children.add(childBoard);
                    //childBoard.printBoard();
                }
                //if (childBoard.getNumBlanks() != 0) {
                //State childState = new State(childBoard, alpha, beta);
                //childState.populateChildren(color * -1);
                //}
            }
        }
    }

    public int getScoreBlack() {
        int total = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j].isBlack()) {
                    total++;
                }
            }
        }
        return total;
    }

    public int getScoreWhite() {
        int total = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j].isWhite()) {
                    total++;
                }
            }
        }
        return total;
    }

    public float getScore(int v) {
        if (v == 1) {
            return (float) (this.getScoreBlack() - this.getScoreWhite() + (.3 * (this.getNumEdge(1) - this.getNumEdge(-1))));
        } else if (v == -1) {
            return (float) (this.getScoreWhite() - this.getScoreBlack() + (.3 * (this.getNumEdge(-1) - this.getNumEdge(1))));
        } else {
            throw new RuntimeException("Invalid color!");
        }
    }

    public int getNumEdge(int v) {
        int total = 0;
        for (int i = 0; i < board.length; i += 7) {
            for (int j = 0; j < board[i].length; j += 7) {
                if (v == 1 && board[i][j].isBlack()) {
                    total++;
                } else if (v == -1 && board[i][j].isWhite()) {
                    total++;
                }
            }
        }
        return total;
    }

    public int getNumBlanks() {
        int total = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j].isBlank()) {
                    total++;
                }
            }
        }
        return total;
    }

    //places a piece
    public boolean putPiece(int x, int y, int color) {
        boolean flip = true;
        if (x < 0 || x >= 8 || y < 0 || y >= 8 || this.getNumBlanks() == 0) {
            return false;
        } else if (board[y][x].isBlank()) {
            //board[y][x].setValue(color);
            if (checkHoriz(x, y, color, flip)
                    || checkVert(x, y, color, flip)
                    || checkDiag(x, y, color, flip)) {
                board[y][x].setValue(color);
                return true;
            }
        }
        return false;
    }

    public Board checkPiece(int x, int y, int color, boolean first) {
        boolean flip = true;
        if (x < 0 || x >= 8 || y < 0 || y >= 8 || this.getNumBlanks() == 0) {
            return null;
        } else if (board[y][x].isBlank()) {
            Board newBoard = new Board(this.getBoard());
            newBoard.getBoard()[y][x].setValue(color);

            if (first) {
                newBoard.move[0] = x;
                newBoard.move[1] = y;
            }

            if (newBoard.checkHoriz(x, y, color, flip)
                    || newBoard.checkVert(x, y, color, flip)
                    || newBoard.checkDiag(x, y, color, flip)) {
                return newBoard;
            }
        }
        return null;
    }

    public boolean checkHoriz(int x, int y, int color, boolean flip) {
        boolean flipped = false;
        //int color = board[y][x].getValue();
        if (color == 0) {
            throw new RuntimeException("Shouldn't have a blank here! checkVert()");
        }

        if (x > 0) {
            ArrayList<Node> potentialCaptures = new ArrayList<>();
            for (int i = x - 1; i >= 0; i--) {
                if (board[y][i].getValue() == color) {
                    for (Node n : potentialCaptures) {
                        if (flip) {
                            n.flip();
                        }
                        flipped = true;
                    }
                    break;
                } else if (!board[y][i].isBlank()) {
                    potentialCaptures.add(board[y][i]);
                } else {
                    break;
                }
            }
        }

        if (x < 8) {
            ArrayList<Node> potentialCaptures = new ArrayList<>();
            for (int i = x + 1; i < 8; i++) {
                if (board[y][i].getValue() == color) {
                    for (Node n : potentialCaptures) {
                        if (flip) {
                            n.flip();
                        }
                        flipped = true;
                    }
                    break;
                } else if (!board[y][i].isBlank()) {
                    potentialCaptures.add(board[y][i]);
                } else {
                    break;
                }
            }
        }
        return flipped;
    }

    public boolean checkVert(int x, int y, int color, boolean flip) {
        boolean flipped = false;
        //int color = board[y][x].getValue();
        if (color == 0) {
            throw new RuntimeException("Shouldn't have a blank here! checkVert()");
        }

        if (y > 0) {
            ArrayList<Node> potentialCaptures = new ArrayList<>();
            for (int i = y - 1; i >= 0; i--) {
                if (board[i][x].getValue() == color) {
                    for (Node n : potentialCaptures) {
                        if (flip) {
                            n.flip();
                        }
                        flipped = true;
                    }
                    break;
                } else if (!board[i][x].isBlank()) {
                    potentialCaptures.add(board[i][x]);
                } else {
                    break;
                }
            }
        }

        if (y < 8) {
            ArrayList<Node> potentialCaptures = new ArrayList<>();
            for (int i = y + 1; i < 8; i++) {
                if (board[i][x].getValue() == color) {
                    for (Node n : potentialCaptures) {
                        if (flip) {
                            n.flip();
                        }
                        flipped = true;
                    }
                    break;
                } else if (!board[i][x].isBlank()) {
                    potentialCaptures.add(board[i][x]);
                } else {
                    break;
                }
            }
        }
        return flipped;
    }

    public boolean checkDiag(int x, int y, int color, boolean flip) {
        boolean flipped = false;
        //int color = board[y][x].getValue();

        if (color == 0) {
            throw new RuntimeException("Shouldn't have a blank here! checkVert()");
        }

        ArrayList<Node> potentialCaptures = new ArrayList<>();

        //down-right
        for (int i = 1; x + i < 8 && y + i < 8; i++) {
            Node position = board[y + i][x + i];
            if (position.getValue() == color) {
                for (Node n : potentialCaptures) {
                    if (flip) {
                        n.flip();
                    }
                    flipped = true;
                }
                break;
            } else if (!position.isBlank()) {
                potentialCaptures.add(position);
            } else {
                break;
            }
        }

        //down-left
        potentialCaptures.clear();
        for (int i = 1; x - i >= 0 && y + i < 8; i++) {
            Node position = board[y + i][x - i];
            if (position.getValue() == color) {
                for (Node n : potentialCaptures) {
                    if (flip) {
                        n.flip();
                    }
                    flipped = true;
                }
                break;
            } else if (!position.isBlank()) {
                potentialCaptures.add(position);
            } else {
                break;
            }
        }

        //up-left
        potentialCaptures.clear();
        for (int i = 1; x - i >= 0 && y - i >= 0; i++) {
            Node position = board[y - i][x - i];
            if (position.getValue() == color) {
                for (Node n : potentialCaptures) {
                    if (flip) {
                        n.flip();
                    }
                    flipped = true;
                }
                break;
            } else if (!position.isBlank()) {
                potentialCaptures.add(position);
            } else {
                break;
            }
        }

        //up-right
        potentialCaptures.clear();
        for (int i = 1; x + i < 8 && y - i >= 0; i++) {
            Node position = board[y - i][x + i];
            if (position.getValue() == color) {
                for (Node n : potentialCaptures) {
                    if (flip) {
                        n.flip();
                    }
                    flipped = true;
                }
                break;
            } else if (!position.isBlank()) {
                potentialCaptures.add(position);
            } else {
                break;
            }
        }
        return flipped;
    }

    public Node[][] getBoard() {
        return board;
    }

    public void setBoard(Node[][] board) {
        this.board = board;
    }

    public void printMove() {
        System.out.println(move[0] + "," + move[1]);
    }

    public void printBoard() {
        System.out.println(this);
    }

    public void printScore() {
        System.out.println("H-Black: " + this.getScore(1) + "\nH-White: " + this.getScore(-1) + "\n");
    }

    @Override
    public String toString() {
        String temp = "    [0] [1] [2] [3] [4] [5] [6] [7]\n";

        for (int i = 0; i < board.length; i++) {
            temp += "[" + i + "] ";
            for (int j = 0; j < board[i].length; j++) {
                String n = " ";
                if (board[i][j].isBlack()) {
                    n = "X";
                } else if (board[i][j].isWhite()) {
                    n = "O";
                }
                temp += "[" + n + "] ";
            }
            temp += "\n";
        }
        temp += "Black: " + this.getScoreBlack() + "\nWhite: " + this.getScoreWhite() + "\n";

        return temp;
    }
}
