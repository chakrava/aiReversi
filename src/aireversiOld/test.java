package aireversiOld;

/**
 *
 * @author Erik Storla
 */
public class test {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Board board = new Board();
        board.printBoard();

        board.putPiece(5, 3, 1);
        board.putPiece(2, 4, 1);
        board.printBoard();

        board = new Board();
        board.printBoard();

        board.putPiece(2, 3, -1);
        board.putPiece(5, 4, -1);
        board.printBoard();

        board = new Board();
        board.printBoard();
        
        board.putPiece(3, 5, 1);
        board.putPiece(4, 2, 1);
        board.printBoard();
        
        board=new Board();
        board.putPiece(0, 0, -1);
        board.printBoard();
    }

}
