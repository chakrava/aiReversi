package aireversi;

/**
 * Represents a single cell on a Reversi board.
 *
 * @author Erik Storla
 */
public class Node {

    private int value;

    /**
     * Construct an empty (0) Node.
     */
    public Node() {
        value = 0;
    }

    /**
     * Construct a Node with value (v). Valid values are -1, 0, 1, and 7
     *
     * @param v the value to set the node to.
     */
    public Node(int v) {
        if ((v < -1 || v > 1) && v != 7) {
            throw new RuntimeException("Invalid range for node, must be -1,0, or 1");
        }
        value = v;
    }

    /**
     * "Flips" a Node to the other player
     */
    public void flip() {
        value *= -1;
    }

    /**
     * Set a Node to black (1)
     */
    public void setBlack() {
        value = 1;
    }

    /**
     * Set a Node to white (-1)
     */
    public void setWhite() {
        value = -1;
    }

    /**
     * Returns if a Node is blank (0)
     *
     * @return if Node is 0
     */
    public boolean isBlank() {
        return value == 0;
    }

    /**
     * Returns if a Node is black (1)
     *
     * @return if Node is 1
     */
    public boolean isBlack() {
        return value == 1;
    }

    /**
     * Returns if a Node is white (-1)
     *
     * @return if a Node is -1
     */
    public boolean isWhite() {
        return value == -1;
    }

    /**
     * Returns the value of a Node
     *
     * @return the value of the Node
     */
    public int getValue() {
        return value;
    }

    /**
     * Sets the value of a Node, valid values are -1, 0, 1, 7
     *
     * @param v the value to set the Node to
     */
    public void setValue(int v) {
        if (v != 7 && (v < -1 || v > 1)) {
            throw new RuntimeException("Invalid range for node, must be -1,0, or 1");
        }
        value = v;
    }

}
