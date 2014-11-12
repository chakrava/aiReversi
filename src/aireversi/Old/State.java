package aireversi.Old;

import aireversi.Board;
import com.sun.jmx.snmp.BerDecoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 *
 * @author Erik Storla
 */
public class State {

    public Board currentBoard;
    public ArrayList<Board> children;
    int alpha = Integer.MAX_VALUE;
    int beta = Integer.MIN_VALUE;

    public State(Board br) {
        currentBoard = new Board();
        children = new ArrayList<>();
//        int alpha = Integer;
//        int beta = b;
    }

    public State(int a, int b) {
        currentBoard = new Board();
        children = new ArrayList<>();
        int alpha = a;
        int beta = b;
    }

    public State(Board br, int a, int b) {
        currentBoard = new Board(br.getBoard());
        children = new ArrayList<>();
        int alpha = a;
        int beta = b;
    }

    public int abprune(Board br, int depth, int a, int b, int color) {
        populateChildren(color);
        if (depth == 0 || children.size() == 0) {
            //return br.getScore(1);
        }
        if(color==1){
            
        }
        return 0;
    }

    public Board minMax(int color) {
        populateChildren(color);
        Collections.sort(children, (Board o1, Board o2) -> {
            if (o1.getScore(color) == o2.getScore(color)) {
                return 0;
            } else if (o1.getScore(color) > o2.getScore(color)) {
                return 1;
            } else {
                return -1;
            }
        });
        if (color == 1) {
            return children.get(0);
        }
        return children.get(children.size());
    }

    public void populateChildren(int color) {
        Board childBoard;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if ((childBoard = currentBoard.checkPiece(i, j, color)) != null) {
                    children.add(childBoard);
                    System.out.println(i + ", " + j);
                    childBoard.printBoard();

                }
                if (childBoard.getNumBlanks() != 0) {
                    //State childState = new State(childBoard, alpha, beta);
                    //childState.populateChildren(color * -1);
                }
            }
        }
    }
}
