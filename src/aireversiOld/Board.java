package aireversiOld;

import java.util.ArrayList;

/**
 *
 * @author Erik Storla
 */
public class Board {

    int[] move = {-1, -1};

    private Node[][] board = new Node[8][8];
    public ArrayList<Board> children = new ArrayList<>();

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

    public Board abprune(Board parent, int depth, int a, int b, int color) {//, ArrayList<Integer[]> steps) {
        populateChildren(color);
        if (depth == 0 || children.isEmpty()) {
            return this;//.getScore(color);
        }

        Board returnBoard = this;
        if (color == 1) {
            while (children.size() > 0) {
                //for (int i = 0; i < children.size() && a < b; i++) {
                Board child = children.remove(0);//(int) (Math.random() * children.size()));
                if (child.getNumBlack() == 0 || child.getNumWhite() == 0) {
                    break;
                }
                //a = Integer.max(a, child.abprune(this, depth - 1, a, b, -1).getScore(color));//,br.steps));
                int prune = child.abprune(this, depth - 1, a, b, -1).getScore(color);
                if (prune > a) {
                    a = prune;
                    returnBoard = child;
                }
                if (a < b) {
                    break;
                }
            }
            parent.alpha = a;
            return returnBoard;//a;
        } else {
            while (children.size() > 0) {
                //for (int i = 0; i < children.size() && a < b; i++) {
                Board child = children.remove(0);//(int) (Math.random() * children.size()));
                //b = Integer.min(b, child.abprune(this, depth - 1, a, b, 1).getScore(color));//,br.steps));
                int prune = child.abprune(this, depth - 1, a, b, 1).getScore(color);
                if (prune < b) {
                    b = prune;
                    returnBoard = child;
                }
                if (a < b) {
                    break;
                }
            }
            parent.beta = b;
            return returnBoard;//b;
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
                if (childBoard != null && this.board[i][j].isBlank()) {
                    //childBoard.putPiece(i, j, color);
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

    public int getNumBlack() {
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

    public int getNumWhite() {
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

    public int getScore(int v) {
        if (v == 1) {
            return (this.getNumBlack() - this.getNumWhite() + (3 * (this.getNumEdge(1) - this.getNumEdge(-1))));
        } else if (v == -1) {
            return (this.getNumWhite() - this.getNumBlack() + (3 * (this.getNumEdge(-1) - this.getNumEdge(1))));
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
        if (x < 0 || x >= 8
                || y < 0 || y >= 8
                || this.getNumBlanks() == 0
                || !board[y][x].isBlank()) {
            return false;
        } else {// if (board[y][x].isBlank()) {
            //board[y][x].setValue(color);
            boolean valid = false;
            if (checkHoriz(x, y, color, flip)) {
                valid = true;
            }
            if (checkVert(x, y, color, flip)) {
                valid = true;
            }
            if (checkDiag(x, y, color, flip)) {
                valid = true;
            }
            if (valid) {
                board[y][x].setValue(color);
                return true;
            }
        }
        return false;
    }

    public Board checkPiece(int x, int y, int color, boolean first) {
        boolean flip = true;
        if (x < 0 || x >= 8 || y < 0 || y >= 8
                || this.getNumBlanks() == 0
                || !board[y][x].isBlank()) {
            return null;
        } else {// if (board[y][x].isBlank()) {
            Board newBoard = new Board(this.getBoard());
            newBoard.getBoard()[y][x].setValue(color);

            if (!first) {
                newBoard.move[0] = this.move[0];
                newBoard.move[1] = this.move[1];
            }

            if (first) {
                newBoard.move[0] = x;
                newBoard.move[1] = y;
            }

            boolean valid = false;
            if (newBoard.checkHoriz(x, y, color, flip)) {
                valid = true;
            }
            if (newBoard.checkVert(x, y, color, flip)) {
                valid = true;
            }
            if (newBoard.checkDiag(x, y, color, flip)) {
                valid = true;
            }
            if (valid) {
                return newBoard;
            }

        }
        return null;
    }

    public boolean checkHoriz(int x, int y, int color, boolean flip) {
        boolean flipped = false;
        //int color = board[y][x].getValue();
        if (color == 0) {
            throw new RuntimeException("Shouldn't have a blank here! checkHoriz()");
        }

        if (x >= 0) {
            ArrayList<Node> potentialCaptures = new ArrayList<>();
            for (int i = x - 1; i >= 0; i--) {
                Node position = board[y][i];
                if (position.isBlank()) {
                    break;
                } else if (position.getValue() == color) {
                    for (Node n : potentialCaptures) {
                        if (flip) {
                            n.flip();
                        }
                        if (potentialCaptures.size() > 0) {
                            flipped = true;
                        }
                    }
                    break;
                } else if (!position.isBlank()) {
                    potentialCaptures.add(position);
                }
            }
        }

        if (x < 8) {
            ArrayList<Node> potentialCaptures = new ArrayList<>();
            for (int i = x + 1; i < 8; i++) {
                Node position = board[y][i];
                if (position.isBlank()) {
                    break;
                } else if (position.getValue() == color) {
                    if (!flip) {
                        flipped = true;
                        break;
                    }
                    for (Node n : potentialCaptures) {
                        if (flip) {
                            n.flip();
                        }
                        if (potentialCaptures.size() > 0) {
                            flipped = true;
                        }
                    }
                    break;
                } else if (!position.isBlank()) {
                    potentialCaptures.add(position);
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

        if (y >= 0) {
            ArrayList<Node> potentialCaptures = new ArrayList<>();
            for (int i = y - 1; i >= 0; i--) {
                Node position = board[i][x];
                if (position.isBlank()) {
                    break;
                } else if (position.getValue() == color) {
                    for (Node n : potentialCaptures) {
                        if (flip) {
                            n.flip();
                        }
                        if (potentialCaptures.size() > 0) {
                            flipped = true;
                        }
                    }
                    break;
                } else if (!position.isBlank()) {
                    potentialCaptures.add(position);
                }
            }
        }

        if (y < 8) {
            ArrayList<Node> potentialCaptures = new ArrayList<>();
            for (int i = y + 1; i < 8; i++) {
                Node position = board[i][x];
                if (position.isBlank()) {
                    break;
                } else if (position.getValue() == color) {
                    for (Node n : potentialCaptures) {
                        if (flip) {
                            n.flip();
                        }
                        if (potentialCaptures.size() > 0) {
                            flipped = true;
                        }
                    }
                    break;
                } else if (!position.isBlank()) {
                    potentialCaptures.add(position);
                }
            }
        }
        return flipped;
    }

    public boolean checkDiag(int x, int y, int color, boolean flip) {
        boolean flipped = false;
        //int color = board[y][x].getValue();

        if (color == 0) {
            throw new RuntimeException("Shouldn't have a blank here!");
        }

        ArrayList<Node> potentialCaptures = new ArrayList<>();

        //down-right
        for (int i = 1; x + i < 8 && y + i < 8; i++) {
            Node position = board[y + i][x + i];
            if (position.isBlank()) {
                break;
            } else if (position.getValue() == color) {
                if (!flip) {
                    flipped = true;
                    break;
                }
                for (Node n : potentialCaptures) {
                    if (flip) {
                        n.flip();
                    }
                    if (potentialCaptures.size() > 0) {
                        flipped = true;
                    }
                }
                //break;
            } else if (!position.isBlank()) {
                potentialCaptures.add(position);
            }
        }

        //down-left
        potentialCaptures.clear();
        for (int i = 1; x - i >= 0 && y + i < 8; i++) {
            Node position = board[y + i][x - i];
            if (position.isBlank()) {
                break;
            } else if (position.getValue() == color) {
                if (!flip) {
                    flipped = true;
                    break;
                }
                for (Node n : potentialCaptures) {
                    if (flip) {
                        n.flip();
                    }
                    if (potentialCaptures.size() > 0) {
                        flipped = true;
                    }
                }
                //break;
            } else if (!position.isBlank()) {
                potentialCaptures.add(position);
            }
        }

        //up-left
        potentialCaptures.clear();
        for (int i = 1; x - i >= 0 && y - i >= 0; i++) {
            Node position = board[y - i][x - i];
            if (position.isBlank()) {
                break;
            } else if (position.getValue() == color) {
                if (!flip) {
                    flipped = true;
                    break;
                }
                for (Node n : potentialCaptures) {
                    if (flip) {
                        n.flip();
                    }
                    if (potentialCaptures.size() > 0) {
                        flipped = true;
                    }
                }
                //break;
            } else if (!position.isBlank()) {
                potentialCaptures.add(position);
            }
        }

        //up-right
        potentialCaptures.clear();
        for (int i = 1; x + i < 8 && y - i >= 0; i++) {
            Node position = board[y - i][x + i];
            if (position.isBlank()) {
                break;
            } else if (position.getValue() == color) {
                if (!flip) {
                    flipped = true;
                    break;
                }
                for (Node n : potentialCaptures) {
                    if (flip) {
                        n.flip();
                    }
                    if (potentialCaptures.size() > 0) {
                        flipped = true;
                    }
                }
                //break;
            } else if (!position.isBlank()) {
                potentialCaptures.add(position);
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
        System.out.println("H-Black: " + this.getScore(1)
                + "\nH-White: " + this.getScore(-1) + "\n");
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
        temp += "Black: " + this.getNumBlack() + " [" + this.getScore(1) + "]"
                + "\nWhite: " + this.getNumWhite() + " [" + this.getScore(-1) + "]" + "\n";

        return temp;
    }
}
